package Laboratori.Laboratorio_3.Esercizio_4;

public class RWext extends RWbasic {
    private int readers;
    private boolean isWriting;
    private boolean newValueWritten;

    public RWext() {
        super();
        readers = 0;
        isWriting = false;
        newValueWritten = false;
    }

    @Override
    public synchronized void write() {
        try {
            while (readers > 0 || isWriting || newValueWritten) {
                wait();
            }
            isWriting = true;
            super.write();
            isWriting = false;
            newValueWritten = true;
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

            if (newValueWritten) {
                newValueWritten = false;
                notifyAll();
            }

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
