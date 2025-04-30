package Laboratori.Laboratorio_4.Server;

public class Main {
    public static void main(String[] args) {
        ServerFIFOUnlimited server = new ServerFIFOUnlimited(4040);
        server.start();
        server.stop();
    }
}
