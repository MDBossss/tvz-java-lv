package com.hr.java.autosalon.controllers;

import com.hr.java.autosalon.components.Role;
import com.hr.java.autosalon.enums.RoleType;
import com.hr.java.autosalon.util.AlertUtils;
import com.hr.java.autosalon.util.LoginDetails;
import com.hr.java.autosalon.main.Main;
import com.hr.java.autosalon.util.LoginUtils;
import com.hr.java.autosalon.util.ScreenUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Controls the login view
 */
public class LoginController{

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label invalidLoginLabel;

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * Attempts to login the user
     */
    public void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        LoginDetails<Role> loginDetails = LoginUtils.validLoginCheck(username,password);

        if(loginDetails.isValid()){
            Thread thread1 = new Thread(() -> {
                System.out.println("Logged in as user: " + loginDetails.getUser().getUsername());
                LoginUtils.updateCurrentUser(loginDetails.getUser().getUsername());
            });
            thread1.start();
            try{
                thread1.join();
            }catch (InterruptedException e){
                logger.info(e.getMessage(),e);
            }

            if (loginDetails.getUser().getRole() == RoleType.ADMIN) {
                showAdminMainScreen();
            } else {
                showUserMainScreen();
            }
        }
        else{
            AlertUtils.displayMessage(invalidLoginLabel,"Invalid login! Try again!",2);
            logger.info("Invalid user login.");
        }
    }

    /**
     * Shows user main screen
     */
    public void showUserMainScreen(){
        ScreenUtils.showUserMainScreen();
    }

    /**
     * Shows admin main screen
     */
    public void showAdminMainScreen(){
        ScreenUtils.showAdminMainScreen();
    }

}
