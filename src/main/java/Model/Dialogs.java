package Model;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;

import java.time.LocalDateTime;


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

    //this method shows users a success message
    public static void showSuccessDialog(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * this method validates that a text field is not empty.
     * displays error if it is empty.
     * @param textField the field to validate.
     * @param errorMessage error message to display if validation fails.
     * @return true if the text field is not empty, false otherwise.
     */
    public static boolean validateTextField(TextField textField, String errorMessage) {
        if (textField.getText().isEmpty()) {
            showErrorDialog("Input Error", errorMessage);
            return false;
        }
        return true;
    }

    /**
     * validates input when adding or updating a customer
     * @param name
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param selectedDivision
     * @return
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

    //validates fields when adding or updating an appt
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
        if (userId <= 0) {
            errorMessage += "No valid user Id!\n";
        }

        if (!errorMessage.isEmpty()) {
            showErrorDialog("Invalid Fields", "Please correct the following errors:\n" + errorMessage);
            return false;
        }
        return true;
    }


}
