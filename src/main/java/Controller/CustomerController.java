package Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerController {

    @FXML
    private Button addCustomerButton;

    @FXML
    private TableColumn<?, ?> addressColumn;

    @FXML
    private TableColumn<?, ?> custIDColumn;

    @FXML
    private TableView<?> customerTableView;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private Button homeButton;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> phoneNumberColumn;

    @FXML
    private TableColumn<?, ?> postalCodeColumn;

    @FXML
    private Button updateCustomerButton;

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

    @FXML
    void handleDeleteCustomerButton(ActionEvent event) {

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
    void handleUpdateCustomerButton(ActionEvent event) {

    }

}

