package Controller;
import DAO.User_DAO_Impl;
import Model.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.util.ResourceBundle;
import java.time.ZoneId;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import java.io.IOException;
import static DAO.Query.*;


public class LogInController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private Label locationLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    private ZoneId userTimeZone;

    public void initialize() {

        //get user's location via zoneID
        userTimeZone = ZoneId.systemDefault();

        //set text of locationLabel to user's location
        locationLabel.setText(userTimeZone.toString());

    }
    @FXML
    void handleSubmitLogIn(ActionEvent event) {

        errorLabel.setText(""); // Clear previous error messages

        // Validate username and password fields
        if (usernameField.getText().trim().isEmpty() && passwordField.getText().trim().isEmpty()) {
            errorLabel.setText(resources.getString("error.both.required"));
        } else if (usernameField.getText().trim().isEmpty()) {
            errorLabel.setText(resources.getString("error.username.required"));
        } else if (passwordField.getText().trim().isEmpty()) {
            errorLabel.setText(resources.getString("error.password.required"));
        } else {
            // Validate credentials
            if (isValidLogin(usernameField.getText(), passwordField.getText())) {
                errorLabel.setText(resources.getString("login.success"));

                //get user details
                Users loggedInUser = getUserByUsername(usernameField.getText());
                System.out.println("Attempting to log in with username: " + usernameField.getText());

                if (loggedInUser != null) {
                    System.out.println("User found: " + loggedInUser.getUserName() + " (User ID: " + loggedInUser.getUserId() + ")");
                    // Check for upcoming appointments via userId
                    ZoneId userTimeZone = ZoneId.systemDefault();
                    System.out.println("User's time zone: " + userTimeZone);
                    User_DAO_Impl.checkForUpcomingAppointments(loggedInUser.getUserId(), userTimeZone);

                    //go to main menu
                    System.out.println("Navigating to directory screen");
                    Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/DirectoryScreen.fxml"));
                        AnchorPane root = loader.load();
                        DirectoryController directoryController = loader.getController();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    errorLabel.setText("User details not found.");
                }
            } else {
                errorLabel.setText(resources.getString("error.login.invalid"));
            }
        }
    }

}
