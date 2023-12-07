module com.example.dbclientapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.dbclientapp to javafx.fxml;
    exports com.example.dbclientapp;
}