package Laboratori.Laboratorio_4.Client;

public class Main {
    public static void main(String[] args) {
        ClientProducer client = new ClientProducer("localhost", 4040);
        client.start();
        //client.sendMessage("producer", "Hello from producer");
        client.stop();
    }
}
