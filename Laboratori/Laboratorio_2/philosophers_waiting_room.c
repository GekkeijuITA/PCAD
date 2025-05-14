#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <time.h>
#include "my_semaphore.h"

#define NUM_PHILOSOPHERS 5

// Semaforo per la sala d'attesa: solo (NUM_PHILOSOPHERS - 1) filosofi possono sedersi contemporaneamente.
my_semaphore room;
// Array di semafori per le forchette: ogni forchetta è disponibile inizialmente (valore 1)
my_semaphore forks[NUM_PHILOSOPHERS];

void* philosopher(void* arg) {
    int id = *((int*)arg);
    free(arg);

    while (1) {
        // Il filosofo sta pensando
        printf("Filosofo %d sta pensando.\n", id);
        sleep(1 + rand() % 3);

        // Entra nella sala d'attesa (se la sala è piena, aspetta)
        my_sem_wait(&room);
        printf("Filosofo %d entra nella sala d'attesa.\n", id);

        // Prova a prendere la forchetta sinistra (numero id) e quella destra (id+1 modulo NUM_PHILOSOPHERS)
        my_sem_wait(&forks[id]);
        printf("Filosofo %d ha preso la forchetta sinistra (%d).\n", id, id);

        my_sem_wait(&forks[(id + 1) % NUM_PHILOSOPHERS]);
        printf("Filosofo %d ha preso la forchetta destra (%d).\n", id, (id + 1) % NUM_PHILOSOPHERS);

        // Il filosofo inizia ad mangiare
        printf("Filosofo %d sta mangiando.\n", id);
        sleep(1 + rand() % 2);

        // Rilascia le forchette in ordine (prima destra, poi sinistra)
        my_sem_signal(&forks[(id + 1) % NUM_PHILOSOPHERS]);
        printf("Filosofo %d ha rilasciato la forchetta destra (%d).\n", id, (id + 1) % NUM_PHILOSOPHERS);
        
        my_sem_signal(&forks[id]);
        printf("Filosofo %d ha rilasciato la forchetta sinistra (%d).\n", id, id);

        // Esce dalla sala d'attesa
        my_sem_signal(&room);
        printf("Filosofo %d esce dalla sala d'attesa.\n\n", id);
    }
    return NULL;
}

int main() {
    srand(time(NULL));
    pthread_t philosophers[NUM_PHILOSOPHERS];

    // Inizializza il semaforo della sala d'attesa a NUM_PHILOSOPHERS - 1
    if (my_sem_init(&room, NUM_PHILOSOPHERS - 1) != 0) {
        fprintf(stderr, "Errore nell'inizializzazione della sala d'attesa\n");
        exit(EXIT_FAILURE);
    }

    // Inizializza ciascun semaforo per le forchette a 1 (disponibile)
    for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
        if (my_sem_init(&forks[i], 1) != 0) {
            fprintf(stderr, "Errore nell'inizializzazione della forchetta %d\n", i);
            exit(EXIT_FAILURE);
        }
    }

    // Crea i thread per ciascun filosofo
    for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
        int *id = malloc(sizeof(int));
        if (!id) {
            perror("Allocazione fallita");
            exit(EXIT_FAILURE);
        }
        *id = i;
        if (pthread_create(&philosophers[i], NULL, philosopher, id) != 0) {
            fprintf(stderr, "Errore nella creazione del thread per il filosofo %d\n", i);
            exit(EXIT_FAILURE);
        }
    }

    // In questo esempio i filosofi girano in ciclo infinito
    for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
        pthread_join(philosophers[i], NULL);
    }

    // Pulizia (non raggiunta, dato il ciclo infinito)
    my_sem_destroy(&room);
    for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
        my_sem_destroy(&forks[i]);
    }

    return 0;
}
