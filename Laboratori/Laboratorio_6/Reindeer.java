package Laboratori.Laboratorio_6;

public class Reindeer implements Runnable {
    private final int id;
    private final SantaClaus santa;

    public Reindeer(int id, SantaClaus santa) {
        this.id = id;
        this.santa = santa;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // in vacanza
                Thread.sleep((long) (Math.random() * 2000));
                System.out.println("Renna " + id + ": sono tornata dal Polo Nord.");
                santa.reindeerBack();
                System.out.println("Renna " + id + ": attendo la distribuzione.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}