#include "my_barrier.h"
#include <stdio.h>
#include <stdlib.h>

#define NUM_THREADS 5
#define NUM_CYCLES 3

unsigned int pthread_my_barrier_init(my_barrier *mb, unsigned int v)
{
    if (v == 0)
        return -1;
    mb->vinit = v;
    mb->val = 0;
    pthread_mutex_init(&mb->lock, NULL);
    pthread_cond_init(&mb->varcond, NULL);
    return 0;
}

unsigned int pthread_my_barrier_wait(my_barrier *mb)
{
    pthread_mutex_lock(&mb->lock);
    mb->val++;
    if (mb->val == mb->vinit)
    {
        mb->val = 0;
        pthread_cond_broadcast(&mb->varcond);
        pthread_mutex_unlock(&mb->lock);
        return 0;
    }
    else
    {
        pthread_cond_wait(&mb->varcond, &mb->lock);
        pthread_mutex_unlock(&mb->lock);
        return 0;
    }
}

// Main di test per la barriera
void *thread_func(void *arg)
{
    int id = *(int *)arg;
    for (int i = 0; i < NUM_CYCLES; i++)
    {
        printf("Thread %d raggiunge la barriera ciclo %d\n", id, i + 1);
        pthread_my_barrier_wait((my_barrier *)arg);
        printf("Thread %d supera la barriera ciclo %d\n", id, i + 1);
    }
    free(arg);
    return NULL;
}

int main()
{
    pthread_t threads[NUM_THREADS];
    my_barrier barrier;
    if (pthread_my_barrier_init(&barrier, NUM_THREADS) != 0)
    {
        fprintf(stderr, "Errore nell'inizializzazione della barriera\n");
        return EXIT_FAILURE;
    }

    // Creazione dei thread che useranno la barriera
    for (int i = 0; i < NUM_THREADS; i++)
    {
        int *id = malloc(sizeof(int));
        *id = i;
        pthread_create(&threads[i], NULL, thread_func, id);
    }

    // Attesa della terminazione dei thread
    for (int i = 0; i < NUM_THREADS; i++)
    {
        pthread_join(threads[i], NULL);
    }

    return EXIT_SUCCESS;
}