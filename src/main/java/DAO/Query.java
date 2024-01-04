package DAO;

import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class Query {

    //method for checking the username against the password for the LogInController
    public static String checkCredentials() {
        return "SELECT Password FROM users WHERE User_Name = ?";
    }

    //method for loading the Appointments TableView for the AppointmentController
    public static ObservableList<Appointments> getAllAppointments() {

        ObservableList<Appointments> appointments = FXCollections.observableArrayList(); //hold all appt objects

        try
            (Connection conn = JDBC.openConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM appointments");
             ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    int appointmentId = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                    LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                    int customerId = rs.getInt("Customer_ID");
                    int userId = rs.getInt("User_ID");
                    int contactId = rs.getInt("Contact_ID");

                    Appointments appointment = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
                    appointments.add(appointment);
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return appointments;
        }

    }

