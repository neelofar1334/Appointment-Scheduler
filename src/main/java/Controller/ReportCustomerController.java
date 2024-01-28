package Controller;
import DAO.ReportsQuery;
import Model.ReportCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for report for total number of appointments for each customer
 */
public class ReportCustomerController {

    @FXML
    private TableView<ReportCustomer> appointmentTableView;
    @FXML
    private Button homeButton;
    @FXML
    private TableColumn<ReportCustomer, String> nameColumn;
    @FXML
    private TableColumn<ReportCustomer, Integer> numAppointmentsColumn;
    @FXML
    private Button reportsButton;

    private ObservableList<ReportCustomer> reportData = FXCollections.observableArrayList();

    public void initialize() {

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        numAppointmentsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfAppointments"));

        loadReportData();

        appointmentTableView.setItems(reportData);
    }

    private void loadReportData() {
        reportData.setAll(ReportsQuery.getReportData());
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
    void handleReportsButton(ActionEvent event) {
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

}
