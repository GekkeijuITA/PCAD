package Laboratori.Laboratorio_3.Esercizio_1;

public class Writer implements Runnable{
    private RWbasic rw;

    public Writer(RWbasic rw) {
        this.rw = rw;
    }

    @Override
    public void run() {
        try {
            System.out.println("Writer: " + Thread.currentThread().getName() + " is writing...");
            Thread.sleep(1000);
            rw.write();  
            System.out.println("Writer: " + Thread.currentThread().getName() + " has written"); 
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
