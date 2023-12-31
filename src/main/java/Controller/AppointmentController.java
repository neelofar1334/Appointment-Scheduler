package Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AppointmentController {

    @FXML
    private TableView<?> apptTableView;

    @FXML
    private TableColumn<?, ?> apptIDColumn;

    @FXML
    private TableColumn<?, ?> titleColumn;

    @FXML
    private TableColumn<?, ?> descriptionColumn;

    @FXML
    private TableColumn<?, ?> locationColumn;

    @FXML
    private TableColumn<?, ?> contactColumn;

    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    private TableColumn<?, ?> startApptColumn;

    @FXML
    private TableColumn<?, ?> endApptColumn;

    @FXML
    private TableColumn<?, ?> userIDColumn;

    @FXML
    private TableColumn<?, ?> customerIDColumn;

    @FXML
    private Button deleteApptButton;

    @FXML
    private Button addApptButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button updateApptButton;

    @FXML
    private RadioButton monthRadioButton;

    @FXML
    private RadioButton weekRadioButton;

    @FXML
    void handleDeleteApptButton(ActionEvent event) {

    }

    @FXML
    void handleaddApptButton(ActionEvent event) {

        int tabToOpenIndex = 0;

        System.out.println("add appointment button clicked");

        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyAppointmentScreen.fxml"));
            TabPane root = loader.load();

            // Access the controller and pass the tab index to open
            ModifyApptController modifyApptController = loader.getController();
            modifyApptController.selectTab(tabToOpenIndex); // Dynamically select the tab

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

    @FXML
    void handleUpdateApptButton(ActionEvent event) {


    }

    @FXML
    void handleMonthView(ActionEvent event) {

    }

    @FXML
    void handleWeekView(ActionEvent event) {

    }

}
