package Controller;
import Model.ReportAppointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for reports menu
 * Three buttons navigating to three different reports on different screens
 */
public class ReportsController {

    @FXML
    private Button additionalReport;
    @FXML
    private Button contactSchedulesReport;
    @FXML
    private Button custApptReport;
    @FXML
    private Button homeButton;

    @FXML
    void handleAdditionalReport(ActionEvent event) {
        System.out.println("total customer appointments report button clicked");

        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ReportCustomerScreen.fxml"));
            AnchorPane root = loader.load();

            // Access the controller
            ReportCustomerController reportCustomerController = loader.getController();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleContactSchedulesReport(ActionEvent event) {
        System.out.println("contact schedule report button clicked");

        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ReportScheduleScreen.fxml"));
            AnchorPane root = loader.load();

            // Access the controller
            ReportScheduleController reportScheduleController = loader.getController();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleCustApptReport(ActionEvent event) {
        System.out.println("customer appointment report button clicked");

        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ReportAppointmentScreen.fxml"));
            AnchorPane root = loader.load();

            // Access the controller
            ReportAppointmentController reportAppointmentController = loader.getController();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleHomeButton(ActionEvent event) {

        System.out.println("home button clicked");

        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/DirectoryScreen.fxml"));
            AnchorPane root = loader.load();

            // Access the controller of the DirectoryScreen
            DirectoryController directoryController = loader.getController();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

