package com.hr.java.autosalon.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Main Class
 */
public class Main extends Application {

    private static Stage mainStage;

    /**
     * Starts the fxml stage
     * @param stage stage
     * @throws IOException IOException
     */
    @Override
    public void start(Stage stage) throws IOException{
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/hr/java/autosalon/view/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1600,900);
        stage.setTitle("Autosalon | Login");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main function
     * @param args args
     */
    public static void main(String[] args){
        launch();
    }

    /**
     * Gets main stage from Main class
     * @return main stage
     */
    public static Stage getMainStage(){
        return mainStage;
    }
}
