package Laboratori.Laboratorio_3.Esercizio_1;

public class Main {
    public static void main(String[] args) {
        try {
            RWbasic rw = new RWbasic();
            Thread []writers = new Thread[50];
            Thread []readers = new Thread[50];

            for (int i = 0; i < 50; i++) {
                writers[i] = new Thread(new Writer(rw), "Writer " + i);
                readers[i] = new Thread(new Reader(rw), "Reader " + i);
            }

            for (int i = 0; i < 50; i++) {
                writers[i].start();
            }

            for (int i = 0; i < 50; i++) {
                readers[i].start();
            }

            for (int i = 0; i < 50; i++) {
                writers[i].join();
            }

            for (int i = 0; i < 50; i++) {
                readers[i].join();
            }

            System.out.println("END");
            System.out.println("The rw's value is " + rw.read());
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
