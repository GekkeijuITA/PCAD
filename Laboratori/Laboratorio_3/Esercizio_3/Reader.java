package Laboratori.Laboratorio_3.Esercizio_3;

public class Reader implements Runnable {

    private RW rw;

    public Reader(RW rw) {
        this.rw = rw;
    }

    @Override
    public void run() {
        try {
            System.out.println("Reader: " + Thread.currentThread().getName() + " is reading...");
            Thread.sleep(1000);
            System.out.println("Reader: " + Thread.currentThread().getName() + " has read: " + rw.read());
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
