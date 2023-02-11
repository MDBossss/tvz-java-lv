package com.hr.java.autosalon.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.Optional;

/**
 * Alert utilities
 */
public class AlertUtils {


    /**
     * Displays confirmation alert
     * @return alert confirmation result
     */
    public static boolean confirmAction(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("POTVRDA");
        alert.setHeaderText("Jeste li sigurni da želite nastaviti s ovom naredbom?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /**
     * Displays error alert
     * @param titleText alert title
     * @param headerText alert header
     * @param contentText alert content
     */
    public static void displayErrorAlert(String titleText,String headerText,String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titleText);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
     * Display message on a label
     * @param label label
     * @param message message
     * @param durationInSeconds duration of message shown
     */
    public static void displayMessage(Label label, String message, int durationInSeconds){
        label.setText(message);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(durationInSeconds), event -> label.setText("")));
        timeline.play();
    }
}
