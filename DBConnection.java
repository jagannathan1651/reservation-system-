package database;

import java.util.ArrayList;
import model.Reservation;

public class DBConnection {
    private static ArrayList<Reservation> reservations = new ArrayList<>();

    public static boolean addReservation(String name, String date, String time, int guests) {
        Reservation r = new Reservation(name, date, time, guests);
        reservations.add(r);
        return true;
    }

    public static ArrayList<Reservation> getAllReservations() {
        return reservations;
    }

    public static boolean cancelReservation(String name) {
        for (Reservation r : reservations) {
            if (r.getName().equalsIgnoreCase(name)) {
                reservations.remove(r);
                return true;
            }
        }
        return false;
    }
}
