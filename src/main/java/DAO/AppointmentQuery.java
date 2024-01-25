package DAO;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
public class AppointmentQuery {

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

    /**
     * method to get all appointments for a specific user
     * @param userId
     * @return
     */
    public static ObservableList<Appointments> getAllAppointmentsForUser(int userId) {
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        String query = "SELECT * FROM appointments WHERE User_ID = ?";

        try (Connection conn = JDBC.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int appointmentId = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    LocalDateTime startUtc = rs.getTimestamp("Start").toLocalDateTime();
                    LocalDateTime endUtc = rs.getTimestamp("End").toLocalDateTime();
                    int customerId = rs.getInt("Customer_ID");
                    int contactId = rs.getInt("Contact_ID");

                    Appointments appointment = new Appointments(appointmentId, title, description, location, type, startUtc, endUtc, customerId, userId, contactId);
                    appointments.add(appointment);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    // Method to get existing contact name for a specific existing appointment
    public static String  getContactNameForAppointment(int appointmentId) {

        String contactName = null;
        String sql = "SELECT contacts.Contact_Name " +
                "FROM appointments " +
                "JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID " +
                "WHERE appointments.Appointment_ID = ?";

        try (Connection conn = JDBC.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, appointmentId); // Set the appointment ID

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                contactName = rs.getString("Contact_Name");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return contactName;
    }

    //method for adding an appt??
    public static boolean addAppointment(String title, String description, String location, String type,
                                         LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
        String startDateTimeStr = start.format(formatter);
        String endDateTimeStr = end.format(formatter);

        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBC.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, location);
            pstmt.setString(4, type);
            pstmt.setString(5, startDateTimeStr);
            pstmt.setString(6, endDateTimeStr);
            pstmt.setInt(7, customerId);
            pstmt.setInt(8, userId);
            pstmt.setInt(9, contactId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    //method for updating an appt
    public static boolean updateAppointment(String title, String description, String location, String type,
                                            LocalDateTime start, LocalDateTime end, int customerId, int userId,
                                            int contactId, int appointmentId) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
        String startDateTimeStr = start.format(formatter);
        String endDateTimeStr = end.format(formatter);

        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " + // Removed extra parenthesis
                "WHERE Appointment_ID = ?";
        try (Connection conn = JDBC.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, location);
            pstmt.setString(4, type);
            pstmt.setString(5, startDateTimeStr);
            pstmt.setString(6, endDateTimeStr);
            pstmt.setInt(7, customerId);
            pstmt.setInt(8, userId);
            pstmt.setInt(9, contactId);
            pstmt.setInt(10, appointmentId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    //maps contact name to its ID
    public static int getContactIdByName(String contactName) {
        String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = ?";
        try (Connection conn = JDBC.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contactName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("Contact_ID");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1; // not found indicator
    }

    // Method to delete selected appointment
    public static boolean deleteAppointment(int appointmentId) {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        try (Connection conn = JDBC.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, appointmentId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

}
