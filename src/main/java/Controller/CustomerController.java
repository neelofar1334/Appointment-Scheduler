package Controller;
import DAO.Query;
import Model.Appointments;
import Model.Customers;
import Model.Dialogs;
import Model.First_level_divisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class CustomerController {

    @FXML
    private TableView<Customers> customerTableView;
    @FXML
    private TableColumn<Customers, Integer> custIDColumn;
    @FXML
    private TableColumn<Customers, String> nameColumn;
    @FXML
    private TableColumn<Customers, String> addressColumn;
    @FXML
    private TableColumn<Customers, String> postalCodeColumn;
    @FXML
    private TableColumn<Customers, String> phoneNumberColumn;
    @FXML
    private TableColumn<First_level_divisions, String> divisionsStringTableColumn;
    @FXML
    private Button addCustomerButton;
    @FXML
    private Button deleteCustomerButton;
    @FXML
    private Button homeButton;
    @FXML
    private Button updateCustomerButton;

    //utility fields
    private ObservableList<Customers> allCustomers = FXCollections.observableArrayList(); //holds all customers from database
    private Customers currentCustomer;

    public void initialize() {

        //set up tableview columns and data
        custIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("custName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        divisionsStringTableColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));

        loadAllCustomers();

        // Add a listener to the table's selection model
        customerTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentCustomer = newSelection;
            }
        });

    }

    /**
     * opens screen to add new customer
     * @param event
     */
    @FXML
    void handleAddCustomerButton(ActionEvent event) {
        int tabToOpenIndex = 0;

        System.out.println("add customer button clicked");

        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyCustomerScreen.fxml"));
            TabPane root = loader.load();

            // Access the controller and pass the tab index to open
            ModifyCustomerController modifyCustomerController = loader.getController();
            modifyCustomerController.selectTab(tabToOpenIndex); // Dynamically select the tab

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * deletes customers with and without appointments
     * @param event
     */
    @FXML
    void handleDeleteCustomerButton(ActionEvent event) {

        Customers selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            int customerId = selectedCustomer.getCustomerId();

            // Attempt to delete all appointments for the customer
            boolean appointmentsDeleted = Query.deleteAppointmentsByCustomerId(customerId);

            // Attempt to delete the customer regardless of whether they had appointments
            if (Query.deleteCustomer(customerId)) {
                Dialogs.showSuccessDialog("Success", "Customer has been deleted.");
                loadAllCustomers(); // Refresh the TableView
            } else {
                if (appointmentsDeleted) {
                    Dialogs.showErrorDialog("Error", "Customer's appointments were deleted, but the customer record could not be deleted.");
                } else {
                    Dialogs.showErrorDialog("Error", "Failed to delete customer.");
                }
            }
        } else {
            Dialogs.showErrorDialog("Selection Error", "Please select a customer to delete.");
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
     * opens screen for updating a customer
     * based on the currently selected customer
     * @param event
     */
    @FXML
    void handleUpdateCustomerButton(ActionEvent event) {

        // Identify which customer has been selected
        Customers selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            try {
                // Load the modify customer screen
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyCustomerScreen.fxml"));
                TabPane root = loader.load();
                ModifyCustomerController modifyCustomerController = loader.getController();

                // Set the current customer and initialize the form
                modifyCustomerController.setCurrentCustomer(selectedCustomer);
                modifyCustomerController.initializeUpdateCustomer(selectedCustomer);

                // Set the scene and show the stage
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));

                // select the update customer tab
                int tabToOpenIndex = 1;
                modifyCustomerController.selectTab(tabToOpenIndex);

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                Dialogs.showErrorDialog("Error", "Failed to load the update screen.");
            }
        } else {
            Dialogs.showErrorDialog("Selection Error", "Please select a customer to update.");
        }
    }


    /**
     * loads tableview with all customers
     */
    private void loadAllCustomers() {
        allCustomers = Query.getAllCustomers();
        customerTableView.setItems(allCustomers);
    }

}

