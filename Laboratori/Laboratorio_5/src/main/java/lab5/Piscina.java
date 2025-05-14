package lab5;

import java.util.concurrent.Semaphore;

public class Piscina {
    private final Semaphore spogliatoi;
    private final Semaphore armadietti;

    public Piscina(int NS, int NC) {
        this.spogliatoi = new Semaphore(NS);
        this.armadietti = new Semaphore(NC);
    }

    public void cliente_deadlook(int id) throws InterruptedException {
        // (a) Prende la chiave di uno spogliatoio
        System.out.printf("Cliente %d: (a) attendo spogliatoio%n", id);
        spogliatoi.acquire();
        System.out.printf("Cliente %d: spogliatoio acquisito%n", id);

        // (b) Prende la chiave di un armadietto
        System.out.printf("Cliente %d: (b) attendo armadietto%n", id);
        armadietti.acquire();
        System.out.printf("Cliente %d: armadietto acquisito%n", id);

        // (c) Si cambia nello spogliatoio
        System.out.printf("Cliente %d: (c) si cambia%n", id);
        Thread.sleep(100);

        // (d) Libera lo spogliatoio
        System.out.printf("Cliente %d: (d) rilascio spogliatoio%n", id);
        spogliatoi.release();

        // (e) Mette i suoi vestiti nell'armadietto
        System.out.printf("Cliente %d: (e) mette i vestiti nell'armadietto%n", id);

        // (f) Restituisce la chiave dello spogliatoio (già gestito a (d))
        System.out.printf("Cliente %d: (f) chiave spogliatoio restituita%n", id);

        // (g) Nuota
        System.out.printf("Cliente %d: (g) nuota%n", id);
        Thread.sleep(1000);

        // (h) Prende la chiave di un altro spogliatoio
        System.out.printf("Cliente %d: (h) attendo spogliatoio%n", id);
        spogliatoi.acquire();
        System.out.printf("Cliente %d: spogliatoio riacquisito%n", id);

        // (i) Recupera i suoi vestiti nell’armadietto
        System.out.printf("Cliente %d: (i) recupera i vestiti%n", id);
        Thread.sleep(100);

        // (j) Si riveste nello spogliatoio
        System.out.printf("Cliente %d: (j) si riveste%n", id);
        Thread.sleep(100);

        // (k) Libera lo spogliatoio
        System.out.printf("Cliente %d: (k) rilascio spogliatoio%n", id);
        spogliatoi.release();

        // (l) Restituisce la chiave dell’armadietto
        System.out.printf("Cliente %d: (l) rilascio armadietto%n", id);
        armadietti.release();

        System.out.printf("Cliente %d: fine servizio%n", id);
    }

    public void cliente_no_deadlook(int id) throws InterruptedException {
        // (a) Prende la chiave di uno spogliatoio
        System.out.printf("Cliente %d: (a) attendo spogliatoio%n", id);
        spogliatoi.acquire();
        System.out.printf("Cliente %d: spogliatoio acquisito%n", id);

        // (c) Si cambia nello spogliatoio
        System.out.printf("Cliente %d: (c) si cambia%n", id);
        Thread.sleep(100);

        // (d) Libera lo spogliatoio
        System.out.printf("Cliente %d: (d) rilascio spogliatoio%n", id);
        spogliatoi.release();

        // SPOSTO PRENDERE CHIAVE ARMADIETTO DOPO LO SPOGLIATOIO dopo che libera lo
        // spogliatoio
        // (b) Prende la chiave di un armadietto
        System.out.printf("Cliente %d: (b) attendo armadietto%n", id);
        armadietti.acquire();
        System.out.printf("Cliente %d: armadietto acquisito%n", id);

        // (e) Mette i suoi vestiti nell'armadietto
        System.out.printf("Cliente %d: (e) mette i vestiti nell'armadietto%n", id);

        // (f) Restituisce la chiave dello spogliatoio (già gestito a (d))
        System.out.printf("Cliente %d: (f) chiave spogliatoio restituita%n", id);

        // (g) Nuota
        System.out.printf("Cliente %d: (g) nuota%n", id);
        Thread.sleep(1000);

        // (h) Prende la chiave di un altro spogliatoio
        System.out.printf("Cliente %d: (h) attendo spogliatoio%n", id);
        spogliatoi.acquire();
        System.out.printf("Cliente %d: spogliatoio riacquisito%n", id);

        // (i) Recupera i suoi vestiti nell’armadietto
        System.out.printf("Cliente %d: (i) recupera i vestiti%n", id);
        Thread.sleep(100);

        // (j) Si riveste nello spogliatoio
        System.out.printf("Cliente %d: (j) si riveste%n", id);
        Thread.sleep(100);

        // (k) Libera lo spogliatoio
        System.out.printf("Cliente %d: (k) rilascio spogliatoio%n", id);
        spogliatoi.release();

        // (l) Restituisce la chiave dell’armadietto
        System.out.printf("Cliente %d: (l) rilascio armadietto%n", id);
        armadietti.release();

        System.out.printf("Cliente %d: fine servizio%n", id);
    }

    public static void main(String[] args) {
        int NS = 2, NC = 2, CLIENTI = 5;
        Piscina p = new Piscina(NS, NC);
        for (int i = 1; i <= CLIENTI; i++) {
            final int id = i;
            new Thread(() -> {
                try {
                    p.cliente_no_deadlook(id);
                } catch (InterruptedException e) {
                }
            }).start();
        }
    }
}