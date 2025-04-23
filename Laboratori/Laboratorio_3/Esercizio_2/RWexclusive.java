package Laboratori.Laboratorio_3.Esercizio_2;

public class RWexclusive extends RWbasic{
    @Override
    public synchronized void write() {
        super.write();
    }

    @Override
    public synchronized int read() {
        return super.read();
    }
}
