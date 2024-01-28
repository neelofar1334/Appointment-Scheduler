package Controller;
import DAO.ReportsQuery;
import Model.ReportAppointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller for report for total number of customer appts by type and month
 */
public class ReportAppointmentController {

        @FXML
        private TableView<ReportAppointment> appointmentReportTable;
        @FXML
        private TableColumn<ReportAppointment, String> typeColumn;
        @FXML
        private TableColumn<ReportAppointment, Integer> monthColumn;
        @FXML
        private TableColumn<ReportAppointment, Integer> countColumn;
        @FXML
        private Button homeButton;
        @FXML
        private Button reportsHomeButton;

        /**
         * initializes tableview columns
         * loads data into tableview
         */
        public void initialize() {

                typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
                monthColumn.setCellValueFactory(new PropertyValueFactory<>("Month"));
                countColumn.setCellValueFactory(new PropertyValueFactory<>("Count"));

                loadReportData();
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
         * loads report data from query
         */
        private void loadReportData() {

                ObservableList<ReportAppointment> reportData = ReportsQuery.getTotalAppointmentsByTypeAndMonth();
                appointmentReportTable.setItems(reportData);
        }

    }
