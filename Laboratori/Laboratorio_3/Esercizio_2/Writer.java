package Laboratori.Laboratorio_3.Esercizio_2;

public class Writer implements Runnable{
    private RWexclusive rw;

    public Writer(RWexclusive rw) {
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
