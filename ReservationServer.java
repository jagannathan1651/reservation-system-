import java.io.*;
import java.net.*;
import database.DBConnection;
import model.Reservation;
import java.util.ArrayList;

public class ReservationServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5050)) {
            System.out.println("🍽 Restaurant Server started on port 5050...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                new ClientHandler(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            String command = (String) in.readObject();

            if (command.equals("ADD")) {
                String name = (String) in.readObject();
                String date = (String) in.readObject();
                String time = (String) in.readObject();
                int guests = (int) in.readObject();
                DBConnection.addReservation(name, date, time, guests);
                out.writeObject("✅ Reservation confirmed for " + name);
            } 
            else if (command.equals("VIEW")) {
                ArrayList<Reservation> list = DBConnection.getAllReservations();
                out.writeObject(list);
            } 
            else if (command.equals("CANCEL")) {
                String name = (String) in.readObject();
                boolean success = DBConnection.cancelReservation(name);
                out.writeObject(success ? "❌ Reservation canceled for " + name : "⚠ No reservation found for " + name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
