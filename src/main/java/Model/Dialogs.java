package Model;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;

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
}
