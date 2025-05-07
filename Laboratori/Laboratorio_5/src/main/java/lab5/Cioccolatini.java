package lab5;

import java.util.concurrent.Semaphore;

public class Cioccolatini {
    private final int P = 5;
    private final Semaphore vuota = new Semaphore(P); // posti liberi
    private final Semaphore pieni = new Semaphore(0); // cioccolatini disponibili

    class Pasticciere implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    vuota.acquire(P); // aspetta che siano vuoti
                    System.out.println("Pasticciere riempie la scatola");
                    Thread.sleep(200);
                    pieni.release(P); // segnala P cioccolatini
                }
            } catch (InterruptedException ignored) {
            }
        }
    }

    class Mangiatore implements Runnable {
        private final int id;

        Mangiatore(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    pieni.acquire(); // aspetta un cioccolatino
                    System.out.printf("Mangiatore %d mangia%n", id);
                    Thread.sleep(100);
                    vuota.release(); // libera uno slot
                }
            } catch (InterruptedException ignored) {
            }
        }
    }

    public static void main(String[] args) {
        Cioccolatini c = new Cioccolatini();
        new Thread(c.new Pasticciere()).start();
        for (int i = 1; i <= 3; i++) {
            new Thread(c.new Mangiatore(i)).start();
        }
    }
}