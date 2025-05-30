package Laboratori.Laboratorio_4.Client;

import java.io.*;
import java.net.*;

public class ClientProducer {
    public static void main(String[] args) {
        int n = 11;
        String host = "localhost";
        int port = 4040;

        Thread[] threads = new Thread[n];

        for (int i = 0; i < n; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                String message = "Messaggio " + (index + 1);
                new ClientProducer().createSocket(host, port, message);
            });

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

    void createSocket(String host, int port, String message) {
        try {
            Socket socket = new Socket(host, port);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            pw.println("producer");
            pw.flush();

            String response = br.readLine();
            System.out.println("Received: " + response);

            if (response.equals("okprod")) {
                pw.println(message);
                pw.flush();

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
