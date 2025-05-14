package Laboratori.Laboratorio_6;

public class Elf implements Runnable {
    private final int id;
    private final SantaClaus santa;

    public Elf(int id, SantaClaus santa) {
        this.id = id;
        this.santa = santa;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // costruisce giocattolo
                Thread.sleep((long) (Math.random() * 1000));
                System.out.println("Elfo " + id + ": ho un problema e vado da Babbo Natale.");
                santa.elfNeedsHelp();
                System.out.println("Elfo " + id + ": problema risolto, torno a lavoro.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}