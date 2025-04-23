package Laboratori.Laboratorio_3.Esercizio_3;

public class RW extends RWbasic {

    private int readers;
    private boolean isWriting;

    public RW() {
        super();
        readers = 0;
        isWriting = false;
    }

    @Override
    public synchronized void write() {
        try {
            while (readers > 0 || isWriting) {
                wait();
            }
            isWriting = true;
            super.write();
            isWriting = false;
            notifyAll();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public synchronized int read() {
        try {
            while (isWriting) {
                wait();
            }
            readers++;
            int tmp = super.read();
            readers--;
            if (readers == 0) {
                notifyAll();
            }
            return tmp;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return -1;
        }
    }
}
