package DAO;
import Model.First_level_divisions;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;

import java.time.LocalDateTime;

/**
 * Utility methods for displaying dialog alerts and validating user inputs.
 */
public class Dialogs {

    /**
     * This method contains a way to alert users to errors with input.
     * @param header the text to be displayed as the dialog's header.
     * @param content text to be displayed as main content of the dialog.
     */
    public static void showErrorDialog(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Displays a success message dialog with a specified header and content.
     * @param header   The text to be displayed as the dialog's header.
     * @param content  The text to be displayed as the main content of the dialog.
     */
    public static void showSuccessDialog(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Validates input fields for adding or updating a customer.
     * @param name           The name of the customer.
     * @param address        The address of the customer.
     * @param postalCode     The postal code of the customer.
     * @param phoneNumber    The phone number of the customer.
     * @param selectedDivision The selected division for the customer.
     * @return true if the input is valid, false otherwise.
     */
    public static boolean isInputValid(String name, String address, String postalCode, String phoneNumber, First_level_divisions selectedDivision) {

        String errorMessage = "";

        if (name == null || name.trim().isEmpty()) {
            errorMessage += "No valid name!\n";
        }

        if (address == null || address.trim().isEmpty()) {
            errorMessage += "No valid address!\n";
        }

        if (postalCode == null || postalCode.trim().isEmpty()) {
            errorMessage += "No valid postal code!\n";
        }

        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            errorMessage += "No valid phone number!\n";
        }

        if (selectedDivision == null) {
            errorMessage += "No division selected!\n";
        }

        if (!errorMessage.isEmpty()) {
            showErrorDialog("Invalid Fields", "Please correct the following errors:\n" + errorMessage);
            return false;
        }
        return true;

    }

    /**
     * Validates fields for adding or updating an appointment.
     * @param title          The title of the appointment.
     * @param description    The description of the appointment.
     * @param location       The location of the appointment.
     * @param contactName    The contact name associated with the appointment.
     * @param type           The type of the appointment.
     * @param start          The start time of the appointment.
     * @param end            The end time of the appointment.
     * @param customerId     The ID of the customer for the appointment.
     * @param userId         The ID of the user associated with the appointment.
     * @return true if the appointment fields are valid, false otherwise.
     */
    public static boolean isApptValid(String title, String description, String location, String contactName, String type, LocalDateTime start, LocalDateTime end,
                                      int customerId, int userId) {
        String errorMessage = "";

        if (title == null || title.trim().isEmpty()) {
            errorMessage += "No valid title!\n";
        }
        if (description == null || description.trim().isEmpty()) {
            errorMessage += "No valid description!\n";
        }
        if (location == null || location.trim().isEmpty()) {
            errorMessage += "No valid location!\n";
        }
        if (contactName == null || contactName.trim().isEmpty()) {
            errorMessage += "No contact name selected!\n";
        }
        if (type == null || type.trim().isEmpty()) {
            errorMessage += "No valid type!\n";
        }
        if (start == null) {
            errorMessage += "No valid start time!\n";
        }
        if (end == null) {
            errorMessage += "No valid end time!\n";
        }
        if (customerId <= 0) {
            errorMessage += "No valid customer Id!\n";
        }
        if (userId < 0 || userId > 2) {
            errorMessage += "Invalid User ID " + userId + "\n";
        }

        if (!errorMessage.isEmpty()) {
            showErrorDialog("Invalid Fields", "Please correct the following errors:\n" + errorMessage);
            return false;
        }
        return true;
    }

    /**
     * Displays an alert for an upcoming appointment.
     * @param appointmentId The ID of the upcoming appointment.
     * @param date          The date and time of the upcoming appointment.
     */
    public static void showUpcomingAppointmentAlert(int appointmentId, LocalDateTime date) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Upcoming Appointment Alert");
        alert.setHeaderText("You have an upcoming appointment.");
        alert.setContentText("Appointment ID: " + appointmentId +
                "\nDate: " + date.toLocalDate() +
                "\nTime: " + date.toLocalTime());
        alert.showAndWait();
    }

    /**
     * Displays an alert indicating that there are no upcoming appointments in the next 15 minutes.
     */
    public static void showNoUpcomingAppointmentsAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Check");
        alert.setHeaderText("No Upcoming Appointments");
        alert.setContentText("You have no upcoming appointments in the next 15 minutes.");
        alert.showAndWait();
    }
}
