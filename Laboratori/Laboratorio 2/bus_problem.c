#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <time.h>
#include "my_semaphore.h"

#define BUS_CAPACITY 3 
#define NUM_PASSENGERS 6 

my_semaphore seat_available;       
my_semaphore boarding_done;        
my_semaphore disembark_permission;
my_semaphore disembark_done;      

void* bus_thread(void* arg) {
    while (1) {
        for (int i = 0; i < BUS_CAPACITY; i++) {
            my_sem_wait(&boarding_done);
        }
        printf("Il bus è pieno. Parto per il tour.\n");

        sleep(3);
        printf("Tour terminato. Autorizzo lo scendere.\n");

        for (int i = 0; i < BUS_CAPACITY; i++) {
            my_sem_signal(&disembark_permission);
        }

        for (int i = 0; i < BUS_CAPACITY; i++) {
            my_sem_wait(&disembark_done);
        }
        printf("Tutti i passeggeri sono scesi. Il bus è pronto per una nuova corsa.\n\n");
    }
    return NULL;
}

void* passenger_thread(void* arg) {
    int id = *((int*)arg);
    free(arg);
    while (1) {
        // Prova a salire sul bus: attende che sia disponibile un posto
        my_sem_wait(&seat_available);
        printf("Il passeggero %d è salito.\n", id);
        // Segnala di essere salito
        my_sem_signal(&boarding_done);

        // Attende l'autorizzazione per scendere
        my_sem_wait(&disembark_permission);
        printf("Il passeggero %d è sceso.\n", id);
        // Segnala al bus di aver sceso
        my_sem_signal(&disembark_done);

        // Libera il posto
        my_sem_signal(&seat_available);

        // Attende un po' prima di tentare di salire nuovamente
        sleep(1 + rand() % 3);
    }
    return NULL;
}

int main() {
    srand(time(NULL));
    pthread_t bus;
    pthread_t passengers[NUM_PASSENGERS];

    if (my_sem_init(&seat_available, BUS_CAPACITY) != 0) {
        fprintf(stderr, "Errore nell'inizializzazione di seat_available\n");
        exit(EXIT_FAILURE);
    }
    if (my_sem_init(&boarding_done, 0) != 0) {
        fprintf(stderr, "Errore nell'inizializzazione di boarding_done\n");
        exit(EXIT_FAILURE);
    }
    if (my_sem_init(&disembark_permission, 0) != 0) {
        fprintf(stderr, "Errore nell'inizializzazione di disembark_permission\n");
        exit(EXIT_FAILURE);
    }
    if (my_sem_init(&disembark_done, 0) != 0) {
        fprintf(stderr, "Errore nell'inizializzazione di disembark_done\n");
        exit(EXIT_FAILURE);
    }

    if (pthread_create(&bus, NULL, bus_thread, NULL) != 0) {
        perror("Errore nella creazione del thread del bus");
        exit(EXIT_FAILURE);
    }

    for (int i = 0; i < NUM_PASSENGERS; i++) {
        int *id = malloc(sizeof(int));
        if (!id) {
            perror("Allocazione fallita");
            exit(EXIT_FAILURE);
        }
        *id = i;
        if (pthread_create(&passengers[i], NULL, passenger_thread, id) != 0) {
            perror("Errore nella creazione del thread del passeggero");
            exit(EXIT_FAILURE);
        }
    }

    pthread_join(bus, NULL);
    for (int i = 0; i < NUM_PASSENGERS; i++) {
        pthread_join(passengers[i], NULL);
    }

    my_sem_destroy(&seat_available);
    my_sem_destroy(&boarding_done);
    my_sem_destroy(&disembark_permission);
    my_sem_destroy(&disembark_done);

    return 0;
}