package com.example.dbclientapp;

import DAO.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.*;
import java.time.temporal.ChronoUnit;
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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/View/LogIn.fxml"), rb);

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(rb.getString("label.login"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
/*
            LocalDateTime startDT = LocalDateTime.of(2024, 01, 20, 19, 0);
            LocalDateTime endDT = LocalDateTime.of(2024, 01, 20, 19, 30);
            LocalDateTime compareDT = LocalDateTime.of(2024, 01, 20, 19, 10);

            //check time overlap
            if(compareDT.isAfter(startDT) && compareDT.isBefore(endDT))
                System.out.println(compareDT + " is between " + startDT + " and " + endDT);
            else if (compareDT.equals(startDT) || compareDT.equals(endDT)) {
                System.out.println("Your date and time matches " + startDT + " or " + endDT);
            }
            else
                System.out.println("your date and time does not overlap");

            //time alert
        LocalTime startTime = LocalTime.of(19, 45);
        LocalTime currentTime = LocalTime.now();
        long timeDifference = ChronoUnit.MINUTES.between(startTime, currentTime);

        System.out.println(timeDifference);

        if (timeDifference > 0 && timeDifference <= 10)
            System.out.println("You have an appointment in " + timeDifference + " minutes");
        else if (timeDifference <= 1) {
            System.out.println("Appointment started " + timeDifference + " minutes ago");
        }

 */

    }
}