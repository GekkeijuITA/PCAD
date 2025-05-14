#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <stdbool.h>

#define PHILOSOPHERS 5
#define EAT_TIMES 5

pthread_mutex_t chopsticks[PHILOSOPHERS];

void *philosopher(void *thread_num)
{
    int id = *(int *)thread_num;
    int left = id;
    int right = (id + 1) % PHILOSOPHERS;
    for (int i = 0; i < EAT_TIMES; i++)
    {
        printf("\nFilosofo %d: sta pensando", id + 1);
        sleep(1);

        pthread_mutex_lock(&chopsticks[left]);
        printf("\nFilosofo %d: ha la sua bacchetta sinistra", id + 1);

        pthread_mutex_lock(&chopsticks[right]);
        printf("\nFilosofo %d: ha la sua bacchetta destra", id + 1);

        printf("\nFilosofo %d: sta mangiando", id + 1);
        sleep(1);

        pthread_mutex_unlock(&chopsticks[left]);
        pthread_mutex_unlock(&chopsticks[right]);
        printf("\nFilosofo %d: ha rilasciato le sue due bacchette", id);
    }
    return NULL;
}

int main()
{
    pthread_t philosophers[PHILOSOPHERS];
    int thread_ids[PHILOSOPHERS];

    // Creazione mutex chopsticks
    for (int i = 0; i < PHILOSOPHERS; i++)
    {
        if (pthread_mutex_init(&chopsticks[i], NULL))
        {
            fprintf(stderr, "Errore nella creazione del mutex %d\n", i);
            exit(EXIT_FAILURE);
        }
    }

    // Creazione tread philosophers
    for (int i = 0; i < PHILOSOPHERS; i++)
    {
        thread_ids[i] = i;
        if (pthread_create(&philosophers[i], NULL, philosopher, (void *)&thread_ids[i]) != 0)
        {
            fprintf(stderr, "Errore nella creazione del thread %d\n", thread_ids[i] + 1);
            exit(EXIT_FAILURE);
        }
    }

    // Eliminazione tread philosophers
    for (int i = 0; i < PHILOSOPHERS; i++)
    {
        if (pthread_join(philosophers[i], NULL))
        {
            fprintf(stderr, "Errore nella cancellazione del thread %d\n", thread_ids[i] + 1);
            exit(EXIT_FAILURE);
        }
    }

    // Eliminazione mutex chopsticks
    for (int i = 0; i < PHILOSOPHERS; i++)
    {
        if (pthread_mutex_destroy(&chopsticks[i]))
        {
            fprintf(stderr, "Errore nella eliminazione del mutex %d\n", i);
            exit(EXIT_FAILURE);
        }
    }
}