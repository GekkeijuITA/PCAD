package Laboratori.Laboratorio_4.Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import Laboratori.Laboratorio_4.Service.ServiceEcho;

public class ServerFIFOLimited {

    public static void main(String[] args) {
        try {
            int PORT = 4040;
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("SERVER: started on port " + PORT);

            LinkedBlockingQueue<String> messages = new LinkedBlockingQueue<>(10);

            while (true) {
                Socket socket = serverSocket.accept();
                ServiceEcho service = new ServiceEcho(socket, messages);
                Thread thread = new Thread(service);
                thread.start();
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
