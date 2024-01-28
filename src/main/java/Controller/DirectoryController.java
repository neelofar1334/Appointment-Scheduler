package Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DirectoryController {

    @FXML
    private Button apptButton;
    @FXML
    private Button customerButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button reportsButton;

    /**
     * navigates to appointment screen/menu
     * @param event
     */
    @FXML
    void handleApptButton(ActionEvent event) {

        System.out.println("appointment button clicked");

        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AppointmentScreen.fxml"));
            AnchorPane root = loader.load();

            // Access the controller
            AppointmentController appointmentController = loader.getController();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * navigates to the customer screen/menu
     * @param event
     */
    @FXML
    void handleCustomerButton(ActionEvent event) {

        System.out.println("customer menu button clicked");

        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CustomerScreen.fxml"));
            AnchorPane root = loader.load();

            // Access the controller
            CustomerController customerController = loader.getController();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * handler for exiting the application
     * @param event
     */
    @FXML
    void handleExitButton(ActionEvent event) {
        System.exit(0);
    }

    /**
     * navigates to the reports screen/menu
     * @param event
     */
    @FXML
    void handleReportsButton(ActionEvent event) {

        System.out.println("reports button clicked");

        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ReportScreen.fxml"));
            AnchorPane root = loader.load();

            // Access the controller
            ReportsController reportsController = loader.getController();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
