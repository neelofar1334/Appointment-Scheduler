package Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyCustomerController {

    @FXML
    private TextField addAddressField;

    @FXML
    private ComboBox<?> addCountryCombo;

    @FXML
    private TextField addCustIDField;

    @FXML
    private Tab addCustomerTab;

    @FXML
    private TextField addNameField;

    @FXML
    private TextField addPhoneField;

    @FXML
    private TextField addPostalCodeField;

    @FXML
    private ComboBox<?> addStateCombo;

    @FXML
    private Button customerMenuButton1;

    @FXML
    private Button customerMenuButton2;

    @FXML
    private Button homeButton1;

    @FXML
    private Button homeButton2;

    @FXML
    private TabPane modifyContact;

    @FXML
    private Button saveButton;

    @FXML
    private Button saveButton2;

    @FXML
    private TextField updateAddressField;

    @FXML
    private ComboBox<?> updateCountryCombo;

    @FXML
    private TextField updateCustIDField;

    @FXML
    private Tab updateCustomerTab;

    @FXML
    private TextField updateNameField;

    @FXML
    private TextField updatePhoneField;

    @FXML
    private TextField updatePostalCodeField;

    @FXML
    private ComboBox<?> updateStateCombo;

    @FXML
    void handleAddCountryCombo(ActionEvent event) {

    }

    @FXML
    void handleAddStateCombo(ActionEvent event) {

    }

    @FXML
    void handleCustomerMenuButton(ActionEvent event) {

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
    void handleUpdateCountryCombo(ActionEvent event) {

    }

    @FXML
    void handleUpdateStateCombo(ActionEvent event) {

    }

    /**
     * this method handles opening to the specified tab
     * Add Customer Tab or the Update Customer Tab
     * @param tabIndex
     */
    public void selectTab(int tabIndex) {
        modifyContact.getSelectionModel().select(tabIndex);
    }

}

