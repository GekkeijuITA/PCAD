CC = gcc
CFLAGS = -Wall -Wextra -pthread

all: philo philo2 barrier

philo: philo.c
	$(CC) $(CFLAGS) -o philo philo.c

philo2: philo2.c
	$(CC) $(CFLAGS) -o philo2 philo2.c

barrier: barrier.c my_barrier.h
	$(CC) $(CFLAGS) -o barrier barrier.c

clean:
	rm -f philo philo2 barrier
