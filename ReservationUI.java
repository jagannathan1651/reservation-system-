package ui;

import javax.swing.*;
import client.ReservationClient;
import model.Reservation;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ReservationUI extends JFrame {
    private JTextField nameField, dateField, timeField, guestsField;
    private JTextArea outputArea;
    private ReservationClient client;

    public ReservationUI() {
        setTitle("🍽 Restaurant Table Reservation System");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Name:"));
        nameField = new JTextField(20);
        add(nameField);

        add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField(10);
        add(dateField);

        add(new JLabel("Time (HH:MM):"));
        timeField = new JTextField(10);
        add(timeField);

        add(new JLabel("Guests:"));
        guestsField = new JTextField(5);
        add(guestsField);

        JButton addButton = new JButton("Reserve Table");
        JButton viewButton = new JButton("View Reservations");
        JButton cancelButton = new JButton("Cancel Reservation");
        outputArea = new JTextArea(15, 45);
        add(addButton);
        add(viewButton);
        add(cancelButton);
        add(new JScrollPane(outputArea));

        try {
            client = new ReservationClient();
            client.connect();
        } catch (Exception e) {
            outputArea.setText("Failed to connect to server.");
        }

        addButton.addActionListener(e -> {
            try {
                String msg = client.addReservation(nameField.getText(), dateField.getText(), timeField.getText(),
                        Integer.parseInt(guestsField.getText()));
                outputArea.setText(msg);
            } catch (Exception ex) {
                outputArea.setText("Error adding reservation!");
            }
        });

        viewButton.addActionListener(e -> {
            try {
                ArrayList<Reservation> list = client.getAllReservations();
                outputArea.setText("Current Reservations:\n");
                for (Reservation r : list) outputArea.append(r.toString() + "\n");
            } catch (Exception ex) {
                outputArea.setText("Error loading reservations!");
            }
        });

        cancelButton.addActionListener(e -> {
            try {
                String msg = client.cancelReservation(nameField.getText());
                outputArea.setText(msg);
            } catch (Exception ex) {
                outputArea.setText("Error canceling reservation!");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ReservationUI();
    }
}
