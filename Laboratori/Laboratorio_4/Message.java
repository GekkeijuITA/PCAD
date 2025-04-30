package Laboratori.Laboratorio_4;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Message implements Serializable{
    public final long timeStamp;
    public final int id;
    public final String role;
    public final String message;
    public final Socket socket;
    public final ObjectOutputStream out;


    public Message(long timeStamp, int id, String role, String message, Socket socket, ObjectOutputStream out) {
        this.timeStamp = timeStamp;
        this.id = id;
        this.role = role;
        this.message = message;
        this.socket = socket;
        this.out = out;
    }
}
