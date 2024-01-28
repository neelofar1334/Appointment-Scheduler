package Controller;
import DAO.Query;
import Model.Countries;
import Model.Customers;
import DAO.Dialogs;
import Model.First_level_divisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for modifying customers including adding and updating
 */
public class ModifyCustomerController implements Initializable {

    @FXML
    private TextField addAddressField;
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
    private ComboBox<First_level_divisions> addStateCombo;
    @FXML
    private ComboBox<Countries> addCountryCombo;
    @FXML
    private TabPane modifyContact;
    @FXML
    private TextField updateAddressField;
    @FXML
    private ComboBox<Countries> updateCountryCombo;
    @FXML
    private ComboBox<First_level_divisions> updateStateCombo;
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

    //utility fields
    private Customers currentCustomer;
    private ObservableList<Countries> allCountries = FXCollections.observableArrayList(); //holds all countries from database
    private ObservableList<First_level_divisions> allDivisions = FXCollections.observableArrayList(); //holds all states from database

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeAddCustomer();
    }

    /**
     * sets current selected customer
     * @param customer
     */
    public void setCurrentCustomer(Customers customer) {
        this.currentCustomer = customer;
    }

    /**
     * initializes form fields for adding a new customer
     */
    public void initializeAddCustomer() {

        //clear or set default values for all fields
        addCustIDField.setText("");
        addNameField.setText("");
        addAddressField.setText("");
        addPostalCodeField.setText("");
        addPhoneField.setText("");

        //populate combo boxes
        addCountryCombo.setItems(FXCollections.observableArrayList(allCountries));
        addStateCombo.setItems(FXCollections.observableArrayList(allDivisions));
        populateCountryCombo();
    }

    /**
     * populates the update customer form with the existing customer's info
     * @param customer
     */
    public void initializeUpdateCustomer(Customers customer){

        //set values for fields from existing customer object
        updateCustIDField.setText(String.valueOf(customer.getCustomerId()));
        updateNameField.setText(customer.getCustName());
        updateAddressField.setText(customer.getCustAddress());
        updatePostalCodeField.setText(customer.getPostalCode());
        updatePhoneField.setText(customer.getPhoneNumber());

        populateCountryCombo(); //populates all countries

        // listener to update divisions when a country is selected
        updateCountryCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // Clear any existing selection in the state combo box
                updateStateCombo.getSelectionModel().clearSelection();

                // Populate divisions based on the new country
                populateDivisionCombo(newVal.getCountryId());
            }
        });

        //fetch country and division/state data for the specific customer
        First_level_divisions customerDivision = Query.getDivisionById(customer.getDivisionId());
        String countryName = customer.getCountryName();

        if (countryName != null && !countryName.isEmpty()) {
            // Find and set the country in the combo box
            for (Countries item : updateCountryCombo.getItems()) {
                if (item.getCountryName().equals(countryName)) {
                    updateCountryCombo.setValue(item);
                    break;
                }
            }
        } else {
            System.out.println("Customer country is null");
        }

        // Set the division after setting the country
        if (customerDivision != null) {
            updateStateCombo.setValue(customerDivision);
        } else {
            System.out.println("Customer division is null");
        }

    }

    /**
     * handles populating the country combo box
     * for adding new customers
     * @param event
     */
    @FXML
    void handleAddCountryCombo(ActionEvent event) {
        Countries selectedCountry = addCountryCombo.getValue();
        if (selectedCountry != null) {
            populateDivisionCombo(selectedCountry.getCountryId());
        }
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

    /**
     * handles saving new customers and adding them to the database
     * @param event
     */
    @FXML
    void handleSaveButton(ActionEvent event) {

            String name = addNameField.getText();
            String address = addAddressField.getText();
            String postalCode = addPostalCodeField.getText();
            String phoneNumber = addPhoneField.getText();
            First_level_divisions selectedDivision = addStateCombo.getValue();

            if (!Dialogs.isInputValid(name, address, postalCode, phoneNumber, selectedDivision)) {
                return; // Exit if validation fails
            }

            int divisionId = selectedDivision.getDivisionId();
            boolean success = Query.addCustomer(name, address, postalCode, phoneNumber, divisionId);
            if (success) {
                Dialogs.showSuccessDialog("Success", "Customer was added");
            } else {
                Dialogs.showErrorDialog("Error", "Customer was not added");
            }

        }

    /**
     * method for saving updates for existing customers
     * @param event
     */
    @FXML
    void handleSaveButton2(ActionEvent event) {

        // Check if a customer is selected
        if (currentCustomer == null) {
            Dialogs.showErrorDialog("Error", "No customer selected for update.");
            return;
        }

        //get data from UI elements
            String name = updateNameField.getText();
            String address = updateAddressField.getText();
            String postalCode = updatePostalCodeField.getText();
            String phoneNumber = updatePhoneField.getText();
            First_level_divisions selectedDivision = updateStateCombo.getValue();

            //validate data
            if (!Dialogs.isInputValid(name, address, postalCode, phoneNumber, selectedDivision)) {
                return; // Exit if validation fails
            }

            int divisionId = selectedDivision.getDivisionId();
            int customerId = currentCustomer.getCustomerId();
            boolean success = Query.updateCustomer(customerId, name, address, postalCode, phoneNumber, divisionId);
            if (success) {
                Dialogs.showSuccessDialog("Success", "Customer was updated");
            } else {
                Dialogs.showErrorDialog("Error", "Customer was not updated");
            }

        }

    /**
     * changes divisions depending on which country is selected
     * @param event
     */
    @FXML
    void handleUpdateCountryCombo(ActionEvent event) {
       Countries selectedCountry = updateCountryCombo.getValue();
       populateDivisionCombo(selectedCountry.getCountryId());
    }

    //utility methods

    /**
     * populates combo box for countries
     */
    private void populateCountryCombo() {
        ObservableList<Countries> allCountries = Query.getAllCountries();
        updateCountryCombo.setItems(allCountries);
        addCountryCombo.setItems(allCountries);
    }

    /**
     * populates division/state box after country is selected
     * @param countryId
     */
    private void populateDivisionCombo(int countryId) {

        ObservableList<First_level_divisions> allDivisions = Query.getAllDivisionsByCountry(countryId);

        // Check if the division list is not empty or null
        if (allDivisions != null && !allDivisions.isEmpty()) {
            updateStateCombo.setItems(allDivisions);
            addStateCombo.setItems(allDivisions);
        } else {
            // If there are no divisions for the selected country, clear the combo boxes
            updateStateCombo.setItems(FXCollections.observableArrayList());
            addStateCombo.setItems(FXCollections.observableArrayList());
        }
    }

    /**
     * this method handles opening to the specified tab
     * Add Customer Tab or the Update Customer Tab
     * @param tabIndex
     */
    public void selectTab(int tabIndex) {
        modifyContact.getSelectionModel().select(tabIndex);
    }

    //doesn't need code-empty handlers for FXML
    @FXML
    void handleUpdateStateCombo(ActionEvent event) {}

    @FXML
    void handleAddStateCombo(ActionEvent event) {}
}

