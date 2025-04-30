package Laboratori.Laboratorio_4.Client;

public class ClientProducer extends Client {

    ClientProducer(String host, int port) {
        super(host, port, "ClientProducer");
    }

    public void start() {
        super.start();
        sendMessage("producer", "Hello from producer");
    }
}
