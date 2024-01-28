package Controller;
import DAO.ReportsQuery;
import Model.Contacts;
import Model.ReportSchedule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDateTime;
import static DAO.Query.getAllContacts;

/**
 * Controller for report for contact schedules
 */
public class ReportScheduleController {

    @FXML
    private TableColumn<ReportSchedule, Integer> apptIdColumn;
    @FXML
    private TableColumn<ReportSchedule, Integer> customerIdColumn;
    @FXML
    private TableColumn<ReportSchedule, String> descriptionColumn;
    @FXML
    private TableColumn<ReportSchedule, LocalDateTime> endColumn;
    @FXML
    private Button homeButton;
    @FXML
    private Button reportsHomeButton;
    @FXML
    private TableView<ReportSchedule> scheduleReportTable;
    @FXML
    private TableColumn<ReportSchedule, LocalDateTime> startColumn;
    @FXML
    private TableColumn<ReportSchedule, String> titleColumn;
    @FXML
    private TableColumn<ReportSchedule, String> typeColumn;
    @FXML
    private ComboBox<String> contactCombo;

    private ObservableList<Contacts> allContacts;

    /**
     * Initializes the controller. This method sets up the table columns
     * and populates the contact combo box.
     */
    public void initialize() {

        apptIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("End"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        populateContactCombo();
        contactCombo.setPromptText("Select a Contact");
    }

    /**
     * Handles the selection of a contact from the ComboBox.
     * Loads the report schedule for the selected contact.
     * @param event ActionEvent associated with the ComboBox selection.
     */
    @FXML
    void handleContactCombo(ActionEvent event){
        String selectedContactName = contactCombo.getValue();
        if (selectedContactName != null) {
            Contacts selectedContact = findContactByName(selectedContactName);
            if (selectedContact != null) {
                loadReportSchedule(selectedContact.getContactId());
            } else {
                scheduleReportTable.getItems().clear();
            }
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
    void handleReportsHomeButton(ActionEvent event) {
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

    /**
     * Loads the report schedule for a specific contact.
     * @param contactId The ID of the contact whose schedule is to be loaded.
     */
    private void loadReportSchedule(int contactId) {
        ObservableList<ReportSchedule> scheduleData = ReportsQuery.getContactSchedule(contactId);
        scheduleReportTable.setItems(scheduleData);
    }

    /**
     * Populates the contact ComboBox with names of all contacts.
     */
    public void populateContactCombo() {
        allContacts = getAllContacts();
        contactCombo.getItems().clear();
        for (Contacts contact : allContacts) {
            contactCombo.getItems().add(contact.getContactName());
        }
    }

    /**
     * Finds a contact by their name.
     * @param name The name of the contact to find.
     * @return The Contacts object if found, or null if not found.
     */
    private Contacts findContactByName(String name) {
        for (Contacts contact : allContacts) {
            if (contact.getContactName().equals(name)) {
                return contact;
            }
        }
        return null;
    }

}

