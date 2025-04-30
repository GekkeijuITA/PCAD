package Laboratori.Laboratorio_4.Server;

import java.net.ServerSocket;

public class Server {
    int PORT;
    ServerSocket serverSocket;

    Server(int port) {
        try {
            PORT = port;
            System.out.println("SERVER: port setted to " + port);
        } catch (Exception e) {
            System.out.println("SERVER: srror starting server(" + e.getMessage() + ")");
        }
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("SERVER: server started on port " + PORT);
        } catch (Exception e) {
            System.out.println("SERVER: error starting server(" + e.getMessage() + ")");
        }
    }

    public void stop() {
        try {
            serverSocket.close();
            System.out.println("SERVER: server stopped");
        } catch (Exception e) {
            System.out.println("SERVER: error stopping server(" + e.getMessage() + ")");
        }
    }
}