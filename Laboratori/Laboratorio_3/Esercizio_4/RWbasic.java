package Laboratori.Laboratorio_3.Esercizio_4;

public class RWbasic {
	private int data;

    public RWbasic() {
        data = 0;
    }

    public int read() {
        return data;
    }

    public void write() {
        int tmp = data;
        tmp++;
        data = tmp;
    }
}