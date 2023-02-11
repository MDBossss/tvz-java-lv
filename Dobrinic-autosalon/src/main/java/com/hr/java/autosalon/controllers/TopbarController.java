package com.hr.java.autosalon.controllers;

import com.hr.java.autosalon.main.Main;
import com.hr.java.autosalon.util.LoginUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controls the top bar view
 */
public class TopbarController {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    @FXML
    private Label currentUserLabel;

    private String currentUser;

    public void initialize(){
        Thread thread1 = new Thread(() -> {
            currentUser = LoginUtils.getCurrentUser();
        });
        thread1.start();
        try{
            thread1.join();
        }catch (InterruptedException e){
            logger.info(e.getMessage(),e);
        }
        currentUserLabel.setText("Logged in as " + currentUser);

    }
}
