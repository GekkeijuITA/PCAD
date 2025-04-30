package Laboratori.Laboratorio_4.Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Laboratori.Laboratorio_4.Message;

public class Client {
    Socket socket;
    String host, client_name;
    int port;

    Client(String host, int port, String client_name) {
        this.host = host;
        this.port = port;
        this.client_name = client_name;
    }

    public void start() {
        try {
            socket = new Socket(host, port);
            System.out.println(client_name.toUpperCase() + ": client started on port " + port);
        } catch (Exception e) {
            System.out.println(client_name.toUpperCase() + ": error starting client(" + e.getMessage() + ")");
        }
    }

    public void stop() {
        try {
            socket.close();
            System.out.println(client_name.toUpperCase() + ": client stopped");
        } catch (Exception e) {
            System.out.println(client_name.toUpperCase() + ": error stopping client(" + e.getMessage() + ")");
        }
    }

    public void sendMessage(String role, String message) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            Message msg = new Message(System.currentTimeMillis(), 0, role, message, socket, out);

            out.writeObject(msg);
            out.flush();

            Message response = (Message) in.readObject();
            System.out.println(client_name.toUpperCase() + ": message sent: " + message);
            System.out.println(client_name.toUpperCase() + ": response received: " + response);
            in.close();
            out.close();
        } catch (Exception e) {
            System.out.println(client_name.toUpperCase() + ": error sending message(" + e.getMessage() + ")");
        }
    }
}
