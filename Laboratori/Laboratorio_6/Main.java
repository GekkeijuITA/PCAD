package Laboratori.Laboratorio_6;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition santaSleep = lock.newCondition();
        Condition elvesWaiting = lock.newCondition();
        Condition reindeerWaiting = lock.newCondition();

        SantaClaus santa = new SantaClaus(lock, santaSleep, elvesWaiting, reindeerWaiting);
        new Thread(santa, "Santa").start();

        // 9 renne
        for (int i = 1; i <= 9; i++) {
            new Thread(new Reindeer(i, santa), "Reindeer-" + i).start();
        }

        // un numero di elfi a scelta, per esempio 10
        for (int i = 1; i <= 10; i++) {
            new Thread(new Elf(i, santa), "Elf-" + i).start();
        }
    }
}