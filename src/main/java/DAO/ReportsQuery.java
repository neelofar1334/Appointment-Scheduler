package DAO;
import Model.ReportAppointment;
import Model.ReportCustomer;
import Model.ReportSchedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class has methods for querying and creating reports from the database.
 */
public class ReportsQuery {

    /**
     * Gets a report of total appointments by type and month.
     * @return An ObservableList of ReportAppointment containing appointment count, type, and month.
     */
    public static ObservableList<ReportAppointment> getTotalAppointmentsByTypeAndMonth() {
        ObservableList<ReportAppointment> reportData = FXCollections.observableArrayList();

        String query = "SELECT COUNT(*), Type, MONTH(Start) as Month FROM appointments GROUP BY Type, MONTH(Start);";
        try (Connection conn = JDBC.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String type = rs.getString("Type");
                int month = rs.getInt("Month");
                int count = rs.getInt(1); // COUNT(*) is the first column
                reportData.add(new ReportAppointment(type, month, count));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return reportData;
    }

    /**
     * Gets schedule for a specific contact.
     * @param contactId The ID of the contact whose schedule is retrieved.
     * @return An ObservableList of ReportSchedule containing the schedule details for the specified contact.
     */
    public static ObservableList<ReportSchedule> getContactSchedule(int contactId) {
        ObservableList<ReportSchedule> schedule = FXCollections.observableArrayList();
        String sql = "SELECT a.Appointment_ID, a.Title, a.Type, a.Description, a.Start, a.End, a.Customer_ID "
                + "FROM appointments a "
                + "WHERE a.Contact_ID = ?";  // Filter by the provided contact ID

        try (Connection conn = JDBC.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, contactId); // Set the contact ID parameter

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    schedule.add(new ReportSchedule(
                            rs.getInt("Appointment_ID"),
                            rs.getString("Title"),
                            rs.getString("Type"),
                            rs.getString("Description"),
                            rs.getTimestamp("Start").toLocalDateTime(),
                            rs.getTimestamp("End").toLocalDateTime(),
                            rs.getInt("Customer_ID")
                    ));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    /**
     * Gets report data for customers, including the number of appointments per customer.
     * @return An ObservableList of ReportCustomer containing customer names and their respective number of appointments.
     */
    public static ObservableList<ReportCustomer> getReportData() {
        ObservableList<ReportCustomer> reportData = FXCollections.observableArrayList();
        String query = "SELECT customer_name, COUNT(*) AS num_appointments FROM appointments JOIN customers ON appointments.customer_id = customers.customer_id GROUP BY customer_name";

        try (Connection conn = JDBC.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String customerName = rs.getString("customer_name");
                int numAppointments = rs.getInt("num_appointments");
                reportData.add(new ReportCustomer(customerName, numAppointments));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return reportData;
    }
}
