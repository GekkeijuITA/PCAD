package Laboratori.Laboratorio_3.Esercizio_4;

public class RWext extends RWbasic {
    private boolean available;
    private Object lock = new Object();

    public RWext() {
        super();
        available = false;
    }

    @Override
    public void write() {
        synchronized (lock) {
            try {
                while (available) {
                    lock.wait();
                }
                super.write();
                available = true;
                lock.notifyAll();
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public int read() {
        synchronized (lock) {
            try {
                while (!available) {
                    lock.wait();
                }
                int tmp = super.read();
                available = false;
                lock.notifyAll();
                return tmp;
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
                return -1;
            }
        }
    }

    public int getData() {
        synchronized (lock) {
            return super.read();
        }
    }
}
