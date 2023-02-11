package com.hr.java.autosalon.util;

import com.hr.java.autosalon.controllers.AdminSidebarController;
import com.hr.java.autosalon.main.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Screen utilities for switching screens
 */
public class ScreenUtils {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * Shows login screen
     */
    public static void showLoginScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/hr/java/autosalon/view/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),1600,900);
            Main.getMainStage().setTitle("Auto-salon | Login");
            Main.getMainStage().setScene(scene);
            Main.getMainStage().show();
        } catch (IOException e){
            e.printStackTrace();
            logger.error("Failed to show LOGIN screen");
        }
    }

    /**
     * Shows user main home screen
     */
    public static void showUserMainScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/hr/java/autosalon/view/home.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),1600,900);
            Main.getMainStage().setTitle("Auto-salon | Home");
            Main.getMainStage().setScene(scene);
            Main.getMainStage().show();
        } catch (IOException e){
            e.printStackTrace();
            logger.error("Failed to show HOME screen");
        }
    }


    /**
     * Shows admin main home screen
     */
    public static void showAdminMainScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/hr/java/autosalon/view/adminHome.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),1600,900);
            Main.getMainStage().setTitle("Auto-salon | Home");
            Main.getMainStage().setScene(scene);
            Main.getMainStage().show();
        } catch (IOException e){
            e.printStackTrace();
            logger.error("Failed to show HOME screen");
        }
    }

    /**
     * Shows available vehicles screen
     */
    public static void showAvailableVehiclesScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader((Main.class.getResource("/com/hr/java/autosalon/view/availableVehicles.fxml")));
            Scene scene = new Scene(fxmlLoader.load(),1600,900);
            Main.getMainStage().setTitle("Auto-salon | Dostupna Vozila");
            Main.getMainStage().setScene(scene);
            Main.getMainStage().show();
        }catch (IOException e){
            e.printStackTrace();
            logger.error("Failed to show AVAILABLE VEHICLES screen");
        }
    }

    /**
     * Shows user available vehicles screen
     */
    public static void showUserAvailableVehiclesScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader((Main.class.getResource("/com/hr/java/autosalon/view/userAvailableVehicles.fxml")));
            Scene scene = new Scene(fxmlLoader.load(),1600,900);
            Main.getMainStage().setTitle("Auto-salon | Dostupna Vozila");
            Main.getMainStage().setScene(scene);
            Main.getMainStage().show();
        }catch (IOException e){
            e.printStackTrace();
            logger.error("Failed to show AVAILABLE VEHICLES screen");
        }
    }

    /**
     * Shows admin add vehicle screen
     */
    public static void showAdminAddVehicleScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader((Main.class.getResource("/com/hr/java/autosalon/view/adminAddVehicle.fxml")));
            Scene scene = new Scene(fxmlLoader.load(),1600,900);
            Main.getMainStage().setTitle("Auto-salon | Admin dodavanje vozila");
            Main.getMainStage().setScene(scene);
            Main.getMainStage().show();
        }catch (IOException e){
            e.printStackTrace();
            logger.error("Failed to show ADMIN ADD VEHICLE screen");
        }
    }

    /**
     * Shows user add vehicle screen
     */
    public static void showUserAddVehicleScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader((Main.class.getResource("/com/hr/java/autosalon/view/userAddVehicle.fxml")));
            Scene scene = new Scene(fxmlLoader.load(),1600,900);
            Main.getMainStage().setTitle("Auto-salon | Korisničko dodavanje vozila");
            Main.getMainStage().setScene(scene);
            Main.getMainStage().show();
        }catch (IOException e){
            e.printStackTrace();
            logger.error("Failed to show USER ADD VEHICLE screen");
        }
    }

    /**
     * Shows user add vehicle screen
     */
    public static void showAdminUserAddVehicleScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader((Main.class.getResource("/com/hr/java/autosalon/view/adminUserAddVehicle.fxml")));
            Scene scene = new Scene(fxmlLoader.load(),1600,900);
            Main.getMainStage().setTitle("Auto-salon | Korisničko dodavanje vozila");
            Main.getMainStage().setScene(scene);
            Main.getMainStage().show();
        }catch (IOException e){
            e.printStackTrace();
            logger.error("Failed to show ADMIN USER ADD VEHICLE screen");
        }
    }

    /**
     * Shows user order history screen
     */
    public static void showUserOrderHistoryScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader((Main.class.getResource("/com/hr/java/autosalon/view/userOrderHistory.fxml")));
            Scene scene = new Scene(fxmlLoader.load(),1600,900);
            Main.getMainStage().setTitle("Auto-salon | Povijest naručenih vozila");
            Main.getMainStage().setScene(scene);
            Main.getMainStage().show();
        }catch (IOException e){
            e.printStackTrace();
            logger.error("Failed to show USER ORDER HISTORY screen");
        }
    }

    /**
     * Shows admin order history screen
     */
    public static void showAdminOrderHistoryScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader((Main.class.getResource("/com/hr/java/autosalon/view/adminOrderHistory.fxml")));
            Scene scene = new Scene(fxmlLoader.load(),1600,900);
            Main.getMainStage().setTitle("Auto-salon | Povijest naručenih vozila");
            Main.getMainStage().setScene(scene);
            Main.getMainStage().show();
        }catch (IOException e){
            e.printStackTrace();
            logger.error("Failed to show USER ORDER HISTORY screen");
        }
    }

    /**
     * Shows admin changes screen
     */
    public static void showAdminChangesScreen(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader((Main.class.getResource("/com/hr/java/autosalon/view/adminChanges.fxml")));
            Scene scene = new Scene(fxmlLoader.load(),1600,900);
            Main.getMainStage().setTitle("Auto-salon | Promijene");
            Main.getMainStage().setScene(scene);
            Main.getMainStage().show();
        }catch (IOException e){
            e.printStackTrace();
            logger.error("Failed to show ADMIN CHANGES screen");
        }
    }
}
