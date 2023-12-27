package com.example.dbclientapp;

import DAO.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //check default locale
        System.out.println(Locale.getDefault());

        //get resource bundle for given locale
        ResourceBundle rb = ResourceBundle.getBundle("lang", Locale.getDefault());

        //Set FXML loader with correct resource bundle
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/LogIn.fxml"), rb);

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(rb.getString("label.login"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();

        JDBC.openConnection();
        JDBC.closeConnection();

    }
}