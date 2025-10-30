package model;

import java.io.Serializable;

public class Reservation implements Serializable {
    private String name;
    private String date;
    private String time;
    private int guests;

    public Reservation(String name, String date, String time, int guests) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.guests = guests;
    }

    public String getName() { return name; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public int getGuests() { return guests; }

    public String toString() {
        return "Name: " + name + " | Date: " + date + " | Time: " + time + " | Guests: " + guests;
    }
}
