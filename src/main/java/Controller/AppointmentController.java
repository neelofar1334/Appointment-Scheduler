package Controller;
import DAO.Query;
import Model.Appointments;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
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

        //method to load all appointments
        loadAllAppointments();

        //activate radio buttons
        viewToggleGroup = new ToggleGroup();
        weekRadioButton.setToggleGroup(viewToggleGroup);
        monthRadioButton.setToggleGroup(viewToggleGroup);

    }

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
        filterAppointments("month");
    }

    @FXML
    void handleWeekView(ActionEvent event) {
        filterAppointments("week");
    }

    /**
     * method for loading tableview with all appointments
     *
     */
    private void loadAllAppointments() {

        allAppointments = Query.getAllAppointments();
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


}
