package Laboratori.Laboratorio_3.Esercizio_1;

public class Reader implements Runnable{

    private RWbasic rw;

    public Reader(RWbasic rw) {
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
