package Controller;
import DAO.Query;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.ResourceBundle;

import static DAO.Query.getAllContacts;

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
    private ComboBox<String> contactNameCombo;
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
    private ComboBox<String> updateContactNameCombo;
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

    //utility fields
    private Appointments currentAppt;
    private ObservableList<Contacts> allContacts = FXCollections.observableArrayList(); //holds all contacts from database
    private ObservableList<Appointments> allAppointments = FXCollections.observableArrayList(); //holds all appointments from database

    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeAddAppt();
        populateContactCombo();
    }

        /**
         * sets current selected appt
         * @param appt
         */
        public void setCurrentAppt(Appointments appt) {
        this.currentAppt = appt;
    }

    public void initializeAddAppt() {

        //clear or set default values for all fields
        apptIDField.setText("");
        titleField.setText("");
        descriptionField.setText("");
        locationField.setText("");
        typeField.setText("");
        startApptField.setText("");
        endApptField.setText("");
        customerIDField.setText("");
        userIDField.setText("");

    }

    public void initializeUpdateAppt(Appointments appointments){

        //set values for fields from existing appt object
        updateApptIDField.setText(String.valueOf(appointments.getAppointmentId()));
        updateTitleField.setText(appointments.getTitle());
        updateDescriptionField.setText(appointments.getDescription());
        updateLocationField.setText(appointments.getLocation());
        updateTypeField.setText(appointments.getType());
        updateStartApptField.setText(String.valueOf(appointments.getStart()));
        updateEndApptField.setText(String.valueOf(appointments.getEnd()));
        updateCustomerIDField.setText(String.valueOf(appointments.getCustomer_ID()));
        updateUserIDField.setText(String.valueOf(appointments.getUser_ID()));

        //get specific contact name for specific appt
        Query query = new Query();
        String contactName = query.getContactNameForAppointment(appointments.getAppointmentId());

        populateContactCombo();

        if (contactName != null) {
            updateContactNameCombo.getSelectionModel().select(contactName);
        }

    }
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

            String selectedContact = String.valueOf(contactNameCombo.getValue());
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

        //handles date/time fields
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);

        //gets contact name for combo box
        String selectedContactName = contactNameCombo.getValue();
        Contacts selectedContact = findContactByName(selectedContactName);

        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();

        if (selectedContact == null) {
            System.out.println("no selected contact");
            return;
        }

        LocalDateTime startAppt, endAppt;

        try {
            System.out.println("Parsing string: '" + startApptField.getText() + "'");
            startAppt = LocalDateTime.parse(startApptField.getText(), formatter);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Debug: Error parsing startAppt");
            return;
        }

        try {
            System.out.println("Parsing string: '" + endApptField.getText() + "'");
            endAppt = LocalDateTime.parse(endApptField.getText(), formatter);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Debug: Error parsing endAppt");
            return;
        }

        int customerId, userId;
        Contacts contacts;
        int contactId = selectedContact.getContactId();
        String contactName = selectedContact.getContactName();

        try {
            customerId = Integer.parseInt(customerIDField.getText());
        } catch (NumberFormatException e) {
            System.out.println("Debug: Error parsing customerId");
            return;
        }

        try {
            userId = Integer.parseInt(userIDField.getText());
        } catch (NumberFormatException e) {
            System.out.println("Debug: Error parsing userId");
            return;
        }

        //validates input
        if (!Dialogs.isApptValid(title, description, location, type, contactName, startAppt, endAppt, customerId, userId)) {
            return;
        }

        // adds appointment with the contactId
        boolean success = Query.addAppointment(title, description, location, type, startAppt, endAppt, customerId, userId, contactId);

        if (success) {
            Dialogs.showSuccessDialog("Success", "Appointment was added");
        } else {
            Dialogs.showErrorDialog("Error", "Appointment was not added");
        }
    }

    /**
     * handles saving updates to existing appts
     * @param event
     */
    @FXML
    void handleSaveUpdatesButton(ActionEvent event) {

        // Check if appt is selected
        if (currentAppt == null) {
            Dialogs.showErrorDialog("Error", "No appointment selected for update.");
            return;
        }

        // Get data from UI elements
        String updateTitle = updateTitleField.getText();
        String updateDescription = updateDescriptionField.getText();
        String updateLocation = updateLocationField.getText();
        String updateType = updateTypeField.getText();
        //String updateContact = updateContactNameCombo.getValue();


        //handles date/time field formatting
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);

        //parse start/end date/time
        LocalDateTime updatedStart = parseDateTime(updateStartApptField.getText(), formatter);
        LocalDateTime updatedEnd = parseDateTime(updateEndApptField.getText(), formatter);
        if (updatedStart == null || updatedEnd == null) {
            System.out.println("Debug: Error parsing dates");
            return;
        }

        // Convert time to UTC
        LocalDateTime updatedStartTime = convertToUTC(updatedStart);
        LocalDateTime updatedEndTime = convertToUTC(updatedEnd);

        //parse customer and user IDs
        int updateCustomerId = parseInteger(updateCustomerIDField.getText());
        int updateUserId = parseInteger(updateUserIDField.getText());
        if (updateCustomerId == -1 || updateUserId == -1) {
            System.out.println("Debug: Error parsing customer/user IDs");
            return;
        }

        // Get contact ID from contact name
        String updateContactName = updateContactNameCombo.getValue();
        System.out.println("Selected contact name: " + updateContactName); // Debugging line
        int contactId = Query.getContactIdByName(updateContactName);
        if (contactId == -1) {
            Dialogs.showErrorDialog("Error", "Contact not found.");
            return;
        }

        // Validate input data
        if (!Dialogs.isApptValid(updateTitle, updateDescription, updateLocation, updateType, updateContactName, updatedStartTime, updatedEndTime, updateCustomerId, updateUserId)) {
            System.out.println("debug: validation is false");
            return;
        }

        // Update the appointment in the database
        boolean success = Query.updateAppointment(updateTitle, updateDescription, updateLocation, updateType, updatedStart, updatedEnd, updateCustomerId, updateUserId, contactId, currentAppt.getAppointmentId());
        if (success) {
            Dialogs.showSuccessDialog("Success", "Appointment updated successfully.");
        } else {
            Dialogs.showErrorDialog("Error", "Failed to update appointment.");
        }
    }

    @FXML
    void handleUpdateContactCombo(ActionEvent event) {

    }

    //populates contact combo box with all contact names
    public void populateContactCombo() {

        // Retrieve the list of contacts
        allContacts = getAllContacts();

        // Clear existing items
        contactNameCombo.getItems().clear();
        updateContactNameCombo.getItems().clear();

        // Add each contact to the combo boxes
        for (Contacts contacts : allContacts) {
            String contactName = contacts.getContactName();
            contactNameCombo.getItems().add(contactName);
            updateContactNameCombo.getItems().add(contactName);
        }
    }

    //method to find a contact by name
    private Contacts findContactByName(String name) {
        for (Contacts contact : allContacts) {
            if (contact.getContactName().equals(name)) {
                return contact;
            }
        }
        return null;
    }


    //converts UTC to local time
    public LocalDateTime convertToLT(LocalDateTime timeUtc) {
        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId localZone = ZoneId.systemDefault();
        return timeUtc.atZone(utcZone).withZoneSameInstant(localZone).toLocalDateTime();
    }

    // Converts local time to UTC
    public LocalDateTime convertToUTC (LocalDateTime timeLocal) {
        ZoneId localZone = ZoneId.systemDefault();
        ZoneId utcZone = ZoneId.of("UTC");
        return timeLocal.atZone(localZone).withZoneSameInstant(utcZone).toLocalDateTime();
    }

    private LocalDateTime parseDateTime(String dateTimeStr, DateTimeFormatter formatter) {
        try {
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int parseInteger(String intStr) {
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
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

