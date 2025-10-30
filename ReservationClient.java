package client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import model.Reservation;

public class ReservationClient {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public void connect() throws IOException {
        socket = new Socket("localhost", 5050);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public String addReservation(String name, String date, String time, int guests) throws Exception {
        out.writeObject("ADD");
        out.writeObject(name);
        out.writeObject(date);
        out.writeObject(time);
        out.writeObject(guests);
        out.flush();
        return (String) in.readObject();
    }

    public ArrayList<Reservation> getAllReservations() throws Exception {
        out.writeObject("VIEW");
        out.flush();
        return (ArrayList<Reservation>) in.readObject();
    }

    public String cancelReservation(String name) throws Exception {
        out.writeObject("CANCEL");
        out.writeObject(name);
        out.flush();
        return (String) in.readObject();
    }

    public void close() throws IOException {
        socket.close();
    }
}
