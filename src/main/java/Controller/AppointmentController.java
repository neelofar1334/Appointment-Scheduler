package Controller;
import DAO.AppointmentQuery;
import DAO.Query;
import DAO.User_DAO_Impl;
import Model.Appointments;
import Model.Customers;
import Model.Dialogs;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

import javafx.scene.control.ToggleGroup;


public class AppointmentController {

    @FXML
    private TableView<Appointments> apptTableView;

    @FXML
    private TableColumn<Appointments, Integer> apptIDColumn;

    @FXML
    private TableColumn<Appointments, String> titleColumn;

    @FXML
    private TableColumn<Appointments, String> descriptionColumn;

    @FXML
    private TableColumn<Appointments, String> locationColumn;

    @FXML
    private TableColumn<Appointments, String> contactColumn;

    @FXML
    private TableColumn<Appointments, String> typeColumn;

    @FXML
    private TableColumn<Appointments, LocalDateTime> startApptColumn;

    @FXML
    private TableColumn<Appointments, LocalDateTime> endApptColumn;

    @FXML
    private TableColumn<Appointments, Integer> userIDColumn;

    @FXML
    private TableColumn<Appointments, Integer> customerIDColumn;

    @FXML
    private Button deleteApptButton;

    @FXML
    private Button addApptButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button updateApptButton;

    @FXML
    private ToggleGroup viewToggleGroup;

    @FXML
    private RadioButton monthRadioButton;

    @FXML
    private RadioButton weekRadioButton;

    private ObservableList<Appointments> allAppointments = FXCollections.observableArrayList(); //holds all appts from database
    private ObservableList<Appointments> filteredAppointments = FXCollections.observableArrayList(); //holds filtered appts based on selection

    public void initialize() {

        //set up tableview columns and data
        apptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        startApptColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
        endApptColumn.setCellValueFactory(new PropertyValueFactory<>("End"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);

        //method to load all appointments
        loadAllAppointments();

        //activate radio buttons
        viewToggleGroup = new ToggleGroup();
        weekRadioButton.setToggleGroup(viewToggleGroup);
        monthRadioButton.setToggleGroup(viewToggleGroup);

    }

    @FXML
    void handleDeleteApptButton(ActionEvent event) {

        //get selected appt
        Appointments selectedAppointment = apptTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Dialogs.showErrorDialog("Error", "No appointment selected");
            return;
        }

        int appointmentId = selectedAppointment.getAppointmentId();
        String appointmentType = selectedAppointment.getType();

        //delete appt
        boolean success = AppointmentQuery.deleteAppointment(selectedAppointment.getAppointmentId());
        if (success) {
            Dialogs.showSuccessDialog("Success", "Appointment cancelled successfully.\nAppointment ID: " + selectedAppointment.getAppointmentId() + "\nType: " + selectedAppointment.getType());
            loadAllAppointments(); // Refresh the TableView
        } else {
            Dialogs.showErrorDialog("Error", "Failed to cancel appointment.");
        }
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
            modifyApptController.populateContactCombo(); //populate contact combo box
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

        // Identify which customer has been selected
        Appointments selectedAppointment = apptTableView.getSelectionModel().getSelectedItem();

        if (selectedAppointment != null) {
            try {
                // Load the modify appt screen
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyAppointmentScreen.fxml"));
                TabPane root = loader.load();
                ModifyApptController modifyApptController = loader.getController();

                // Set the current appt and initialize the form
                modifyApptController.setCurrentAppt(selectedAppointment);
                modifyApptController.initializeUpdateAppt(selectedAppointment);

                // Set the scene and show the stage
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));

                // select the update appt tab
                int tabToOpenIndex = 1;
                modifyApptController.selectTab(tabToOpenIndex);

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                Dialogs.showErrorDialog("Error", "Failed to load the update screen.");
            }
        } else {
            Dialogs.showErrorDialog("Selection Error", "Please select an appointment to update.");
        }

    }

    @FXML
    void handleMonthView(ActionEvent event) {
        filterAppointments("month");
    }

    @FXML
    void handleWeekView(ActionEvent event) {
        filterAppointments("week");
    }

    private void loadAllAppointments() {
        allAppointments = AppointmentQuery.getAllAppointments();
        apptTableView.setItems(allAppointments);
    }


    /**
     * method to filter appointments depending on the date, for the monthly and weekly radio buttons
     *
     */
    private void filterAppointments(String period) {

        filteredAppointments.clear();
        LocalDate now = LocalDate.now();

        for (Appointments appt : allAppointments) {
            LocalDate apptDate = appt.getStart().toLocalDate();
            if ("week".equals(period) && isThisWeek(apptDate, now)) {
                filteredAppointments.add(appt);
            } else if ("month".equals(period) && isThisMonth(apptDate, now)) {
                filteredAppointments.add(appt);
            }
        }
        apptTableView.setItems(filteredAppointments);
    }

    /**
     * Methods to determine if a date is within the current week and month
     * @param Start
     * @param now
     * @return
     */
    private boolean isThisWeek(LocalDate Start, LocalDate now) {

        LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        return !Start.isBefore(startOfWeek) && !Start.isAfter(endOfWeek);
    }

    private boolean isThisMonth(LocalDate Start, LocalDate now) {

        YearMonth yearMonth = YearMonth.from(now);
        LocalDate startOfMonth = yearMonth.atDay(1);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        return !Start.isBefore(startOfMonth) && !Start.isAfter(endOfMonth);
    }

    public void refreshTableView() {

        allAppointments.clear();
        allAppointments.addAll(AppointmentQuery.getAllAppointments());

        for (Appointments appointment : allAppointments) {
            // Convert UTC times to local times
            LocalDateTime startLocal = User_DAO_Impl.convertFromUTC(appointment.getStart(), ZoneId.systemDefault());
            LocalDateTime endLocal = User_DAO_Impl.convertFromUTC(appointment.getEnd(), ZoneId.systemDefault());
            appointment.setStart(startLocal);
            appointment.setEnd(endLocal);
        }

        apptTableView.setItems(allAppointments);
    }


}
