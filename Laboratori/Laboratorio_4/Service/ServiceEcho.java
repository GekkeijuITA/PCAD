package Laboratori.Laboratorio_4.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class ServiceEcho implements Runnable {
    private Socket socket;
    private LinkedBlockingQueue<String> messages;

    public ServiceEcho(Socket socket, LinkedBlockingQueue<String> messages) {
        this.socket = socket;
        this.messages = messages;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            String clientType = br.readLine();

            if (clientType.equals("producer")) {
                pw.println("okprod");
                pw.flush();

                String message = br.readLine();
                if(message != null) {
                    messages.put(message);
                    System.out.println("Received message: " + message);
                    pw.println("accepted");
                    pw.flush();
                } else {
                    System.out.println("Received null message from producer.");
                }

            } else if (clientType.equals("consumer")) {
                pw.println("okcons");
                pw.flush();
                try {
                    String message = messages.take();
                    pw.println(message);
                    pw.flush();   
                    System.out.println("Sent message to consumer: " + message);
                } catch (Exception e) {
                    System.out.println("Error while sending message to consumer: " + e.getMessage());
                }
            }

            br.close();
            pw.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Service Echo: " + e);
            e.printStackTrace();
        }
    }
}
