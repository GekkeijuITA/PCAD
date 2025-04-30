package Laboratori.Laboratorio_4.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import Laboratori.Laboratorio_4.Message;

public class ServerFIFOUnlimited extends Server {

    private ArrayList<Message> messages = new ArrayList<>();

    public ServerFIFOUnlimited(int port) {
        super(port);
    }

    @Override
    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("SERVER FIFO UNLIMITED: server started on port " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                Message message = (Message) in.readObject();

                //messages.add(new Message(message.timeStamp, message.id, message.role, socket, out));

                // Risposta al client
                out.writeObject("SERVER FIFO UNLIMITED: message received: " + message.message);
                out.flush();

                // Log di debug
                System.out.println("SERVER FIFO UNLIMITED: message received: " + message);
                out.close();
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("SERVER FIFO UNLIMITED: error starting server(" + e.getMessage() + ")");
        }
    }
}
