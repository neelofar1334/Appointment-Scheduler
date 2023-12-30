package Controller;
import DAO.JDBC;
import DAO.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.time.ZoneId;

public class LogInController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private Label locationLabel;

    @FXML
    private Label loginLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    public void initialize() {

        //get user's location via zoneID
        ZoneId zoneId = ZoneId.systemDefault();

        //set text of locationLabel to user's location
        locationLabel.setText(zoneId.toString());




    }
    @FXML
    void handleSubmitLogIn(ActionEvent event) {

        errorLabel.setText(""); // Clear previous error messages

        //validate username and password field
        if (usernameField.getText().trim().isEmpty() && passwordField.getText().trim().isEmpty()) {
            errorLabel.setText(resources.getString("error.both.required"));
            //validate username field only
        } else if (usernameField.getText().trim().isEmpty()) {
            errorLabel.setText(resources.getString("error.username.required"));
            //validate password field only
        } else if (passwordField.getText().trim().isEmpty()) {
            errorLabel.setText(resources.getString("error.password.required"));
        }
        //validate credentials
        if (isValidLogin(usernameField.getText(), passwordField.getText())) {
            errorLabel.setText(resources.getString("login.success"));
        } else {
            errorLabel.setText(resources.getString("error.login.invalid"));
        }
    }

        /**
         * Validates user's credentials.
         *
         * @param userName the username in the database
         * @param password the password in the database
         * @return true if the credentials are valid, false otherwise
         */

        private boolean isValidLogin(String userName, String password) {

            Connection conn = null;

            try {
                // connect to database
                conn = JDBC.openConnection();

                //use checkCredentials query from Query class
                PreparedStatement pstmt = conn.prepareStatement(Query.checkCredentials());
                pstmt.setString(1, userName);
                ResultSet rs = pstmt.executeQuery();


                if (rs.next()) {
                    // Check password
                    String retrievedPassword = rs.getString("Password");
                    if (retrievedPassword != null && retrievedPassword.equals(password)) {
                        return true; // The password matches
                    }
                }
                // Handle exceptions
            } catch (SQLException e) {
                e.printStackTrace(); //prints stack trace of SQLException
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } finally {
                JDBC.closeConnection(conn);
            }
            return false;
        }


}
