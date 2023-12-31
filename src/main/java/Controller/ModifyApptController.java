package Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyApptController {

    //This screen contains two tabs
    //The Add Appt Tab and the Update Appt Tab

    //Add Appt FXML

    @FXML
    private TabPane modifyAppointmentTabPane;

    @FXML
    private Tab addAppointmentTab;

    @FXML
    private TextField apptIDField;

    @FXML
    private Button apptsMenuButton1;

    @FXML
    private ComboBox<?> contactNameCombo;

    @FXML
    private TextField customerIDField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField endApptField;

    @FXML
    private Button homeButton1;

    @FXML
    private TextField locationField;

    @FXML
    private Button saveButton;

    @FXML
    private Button saveUpdatesButton;

    @FXML
    private TextField startApptField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField typeField;

    @FXML
    private TextField userIDField;

    //Update Appt Tab FXML
    @FXML
    private Tab updateAppointmentTab;

    @FXML
    private Button apptsMenuButton2;

    @FXML
    private TextField updateApptIDField;

    @FXML
    private ComboBox<?> updateContactNameCombo;

    @FXML
    private TextField updateCustomerIDField;

    @FXML
    private TextField updateDescriptionField;

    @FXML
    private TextField updateEndApptField;

    @FXML
    private TextField updateLocationField;

    @FXML
    private TextField updateStartApptField;

    @FXML
    private TextField updateTitleField;

    @FXML
    private TextField updateTypeField;

    @FXML
    private TextField updateUserIDField;

    @FXML
    private Button homeButton2;


    @FXML
    void handleApptsMenuButton(ActionEvent event) {

        System.out.println("appointment menu button clicked");

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

    @FXML
    void handleContactCombo(ActionEvent event) {

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
    void handleSaveButton(ActionEvent event) {

    }

    @FXML
    void handleSaveUpdatesButton(ActionEvent event) {

    }

    @FXML
    void handleUpdateContactCombo(ActionEvent event) {

    }

    /**
     * a method to control which tab is being selected
     * Add appt tab or the update appt tab
     * @param tabIndex
     */
    public void selectTab(int tabIndex) {
        modifyAppointmentTabPane.getSelectionModel().select(tabIndex);
    }

}

