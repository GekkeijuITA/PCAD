package Laboratori.Laboratorio_6;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SantaClaus implements Runnable {
    private final Lock lock;
    private final Condition santaSleep;
    private final Condition elvesWaiting;
    private final Condition reindeerWaiting;
    private int waitingElves = 0;
    private int waitingReindeer = 0;

    public SantaClaus(Lock lock, Condition santaSleep, Condition elvesWaiting, Condition reindeerWaiting) {
        this.lock = lock;
        this.santaSleep = santaSleep;
        this.elvesWaiting = elvesWaiting;
        this.reindeerWaiting = reindeerWaiting;
    }

    @Override
    public void run() {
        try {
            while (true) {
                lock.lock();
                while (waitingReindeer < 9 && waitingElves < 3) {
                    santaSleep.await();
                }
                if (waitingReindeer == 9) {
                    // Wake up to deliver toys
                    System.out.println("Santa: tutte le renne sono qui, parto per la consegna!");
                    // simulazione consegna
                    Thread.sleep(1000);
                    // reset contatore renne
                    waitingReindeer = 0;
                    // sveglia renne
                    reindeerWaiting.signalAll();
                } else if (waitingElves == 3) {
                    // Wake up to help elves
                    System.out.println("Santa: tre elfi hanno bisogno di aiuto.");
                    // simulazione aiuto
                    Thread.sleep(500);
                    // dopo aver aiutato
                    waitingElves = 0;
                    elvesWaiting.signalAll();
                }
                lock.unlock();
                // torna a dormire
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void elfNeedsHelp() throws InterruptedException {
        lock.lock();
        while (waitingElves >= 3) {
            elvesWaiting.await();
        }
        waitingElves++;
        if (waitingElves == 3) {
            santaSleep.signal();
        }
        // aspetta di essere aiutato
        while (waitingElves > 0) {
            elvesWaiting.await();
        }
        lock.unlock();
    }

    public void reindeerBack() throws InterruptedException {
        lock.lock();
        waitingReindeer++;
        if (waitingReindeer == 9) {
            santaSleep.signal();
        }
        // aspetta consegna finita
        while (waitingReindeer > 0) {
            reindeerWaiting.await();
        }
        lock.unlock();
    }
}