package Laboratori.Laboratorio_4.Client;

import java.io.*;
import java.net.*;

public class ClientConsumer {
    public static void main(String[] args) {
        int n = 1;
        String host = "localhost";
        int port = 4040;

        Thread[] threads = new Thread[n];

        for (int i = 0; i < n; i++) {
            threads[i] = new Thread(() -> new ClientConsumer().createSocket(host, port));

            threads[i].start();
        }

        for (int i = 0; i < n; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void createSocket(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            pw.println("consumer");
            pw.flush();

            String response= br.readLine();
            System.out.println("Received: " + response);

            if(response.equals("okcons")) {
                response = br.readLine();
                System.out.println("Received message: " + response);
            }          

            pw.close();
            br.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
