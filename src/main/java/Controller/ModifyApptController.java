package Controller;
import DAO.AppointmentQuery;
import DAO.Query;
import DAO.User_DAO_Impl;
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
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import static DAO.Query.getAllContacts;
import java.time.ZoneId;

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
        String contactName = AppointmentQuery.getContactNameForAppointment(appointments.getAppointmentId());

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

    /**
     * handles saving a new appointment
     * @param event
     */
    @FXML
    void handleSaveButton(ActionEvent event) {

        // Parse the start and end date/time from UI elements
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
        LocalDateTime startAppt, endAppt;

        try {
            startAppt = LocalDateTime.parse(startApptField.getText(), formatter);
            endAppt = LocalDateTime.parse(endApptField.getText(), formatter);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Debug: Error parsing start/end time");
            Dialogs.showErrorDialog("Error", "Invalid date/time format.");
            return;
        }

        // Get contact details
        String selectedContactName = contactNameCombo.getValue();
        Contacts selectedContact = findContactByName(selectedContactName);
        if (selectedContact == null) {
            System.out.println("no selected contact");
            Dialogs.showErrorDialog("Error", "No contact selected.");
            return;
        }

        int customerId = -1;
        int userId = -1;

        try {
            // Parse customer ID
            customerId = Integer.parseInt(customerIDField.getText());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing customer ID: " + e.getMessage());
            Dialogs.showErrorDialog("Error", "Invalid customer ID format.");
            return;
        }

        try {
            // Parse user ID
            userId = Integer.parseInt(userIDField.getText());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing user ID: " + e.getMessage());
            Dialogs.showErrorDialog("Error", "Invalid user ID format.");
            return;
        }

        // User's local time zone
        ZoneId userLocalZoneId = ZoneId.systemDefault();

        // Convert local times to ET for business hour validation
        LocalDateTime startET = User_DAO_Impl.convertToET(startAppt, userLocalZoneId);
        LocalDateTime endET = User_DAO_Impl.convertToET(endAppt, userLocalZoneId);

        // Validate business hours in ET
        if (!User_DAO_Impl.isWithinBusinessHours(startET) || !User_DAO_Impl.isWithinBusinessHours(endET)) {
            Dialogs.showErrorDialog("Error", "Appointment time must be within business hours (8:00 AM to 10:00 PM ET).");
            return;
        }

        // Convert ET times to local times for table view
        startAppt = User_DAO_Impl.convertFromET(startET, userLocalZoneId);
        endAppt = User_DAO_Impl.convertFromET(endET, userLocalZoneId);

        // Check for overlapping appointments
        if (hasOverlappingAppointment(customerId, startAppt, endAppt, -1)) {
            Dialogs.showErrorDialog("Error", "This appointment overlaps with another appointment for this customer.");
            return;
        }

        // Convert local times to UTC for storage
        LocalDateTime startUTC = User_DAO_Impl.convertToUTC(startAppt, ZoneId.systemDefault());
        LocalDateTime endUTC = User_DAO_Impl.convertToUTC(endAppt, ZoneId.systemDefault());

        // Validates input
        if (!Dialogs.isApptValid(titleField.getText(), descriptionField.getText(), locationField.getText(), typeField.getText(), selectedContact.getContactName(), startAppt, endAppt, customerId, userId)) {
            return;
        }

        // Adds appointment with the contactId
        boolean success = AppointmentQuery.addAppointment(titleField.getText(), descriptionField.getText(), locationField.getText(), typeField.getText(), startUTC, endUTC, customerId, userId, selectedContact.getContactId());
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
        // Check if an appointment is selected
        if (currentAppt == null) {
            Dialogs.showErrorDialog("Error", "No appointment selected for update.");
            return;
        }

        // Get data from UI elements
        String updateTitle = updateTitleField.getText();
        String updateDescription = updateDescriptionField.getText();
        String updateLocation = updateLocationField.getText();
        String updateType = updateTypeField.getText();

        // User's local time zone
        ZoneId userLocalZoneId = ZoneId.systemDefault();

        // Parse the start and end date/time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
        LocalDateTime updatedStart, updatedEnd;
        try {
            updatedStart = LocalDateTime.parse(updateStartApptField.getText(), formatter);
            updatedEnd = LocalDateTime.parse(updateEndApptField.getText(), formatter);
        } catch (DateTimeParseException e) {
            Dialogs.showErrorDialog("Error", "Invalid date/time format.");
            return;
        }

        // Convert local times to ET for business hour validation
        LocalDateTime updatedStartET = User_DAO_Impl.convertToET(updatedStart, userLocalZoneId);
        LocalDateTime updatedEndET = User_DAO_Impl.convertToET(updatedEnd, userLocalZoneId);

        // Validate business hours in ET
        if (!User_DAO_Impl.isWithinBusinessHours(updatedStartET) || !User_DAO_Impl.isWithinBusinessHours(updatedEndET)) {
            Dialogs.showErrorDialog("Error", "Appointment time must be within business hours (8:00 AM to 10:00 PM ET).");
            return;
        }

        // Convert ET times to local times for table view
        updatedStart = User_DAO_Impl.convertFromET(updatedStartET, userLocalZoneId);
        updatedEnd = User_DAO_Impl.convertFromET(updatedEndET, userLocalZoneId);

        // Get info from the current appointment
        int customerId = currentAppt.getCustomer_ID();
        int appointmentId = currentAppt.getAppointmentId();

        // Check for overlapping appointments
        if (hasOverlappingAppointment(customerId, updatedStart, updatedEnd, appointmentId)) {
            Dialogs.showErrorDialog("Error", "This appointment overlaps with another appointment for this customer.");
            return;
        }

        // Convert local times to UTC for storage
        LocalDateTime startApptUtc = User_DAO_Impl.convertToUTC(updatedStart, ZoneId.systemDefault());
        LocalDateTime endApptUtc = User_DAO_Impl.convertToUTC(updatedEnd, ZoneId.systemDefault());

        int updateCustomerId = -1;
        int updateUserId = -1;
        int contactId = -1;

        try {
            // Parse customer ID
            updateCustomerId = Integer.parseInt(updateCustomerIDField.getText());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing customer ID: " + e.getMessage());
            Dialogs.showErrorDialog("Error", "Invalid customer ID format.");
            return;
        }

        try {
            // Parse user ID
            updateUserId = Integer.parseInt(updateUserIDField.getText());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing user ID: " + e.getMessage());
            Dialogs.showErrorDialog("Error", "Invalid user ID format.");
            return;
        }

        try {
            // Get contact ID
            String updateContactName = updateContactNameCombo.getValue();
            contactId = AppointmentQuery.getContactIdByName(updateContactName);
            if (contactId == -1) {
                throw new IllegalArgumentException("Contact not found.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            Dialogs.showErrorDialog("Error", e.getMessage());
            return;
        }

        String updateContactName = updateContactNameCombo.getValue();

        // Validate input data
        if (!Dialogs.isApptValid(updateTitle, updateDescription, updateLocation, updateType, updateContactName, updatedStart, updatedEnd, updateCustomerId, updateUserId)) {
            return;
        }

        // Update the appointment in the database
        boolean success = AppointmentQuery.updateAppointment(updateTitle, updateDescription, updateLocation, updateType, startApptUtc, endApptUtc, updateCustomerId, updateUserId, contactId, currentAppt.getAppointmentId());
        if (success) {
            Dialogs.showSuccessDialog("Success", "Appointment updated successfully.");
            currentAppt.setStart(updatedStart);
            currentAppt.setEnd(updatedEnd);
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

    public boolean hasOverlappingAppointment(int customerId, LocalDateTime updatedStart, LocalDateTime updatedEnd, int appointmentIdToExclude) {
        List<Appointments> allAppointments = AppointmentQuery.getAllAppointments();

        for (Appointments appointment : allAppointments) {
            // Check if the appt is for the same customer and not the one to exclude
            if (appointment.getCustomer_ID() == customerId && (appointmentIdToExclude == -1 || appointment.getAppointmentId() != appointmentIdToExclude)) {
                LocalDateTime start = appointment.getStart();
                LocalDateTime end = appointment.getEnd();

                // Check for overlap
                boolean overlaps = !updatedStart.isAfter(end) && !updatedEnd.isBefore(start);
                if (overlaps) {
                    return true;
                }
            }
        }

        return false;
    }



}

