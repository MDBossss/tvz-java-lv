package com.hr.java.autosalon.controllers;

import com.hr.java.autosalon.util.ScreenUtils;

/**
 * Controls the sidebar view
 */
public class SidebarController {

    /**
     * Shows login screen
     */
    public void showLoginScreen(){
        ScreenUtils.showLoginScreen();
    }

    /**
     * Shows user main screen
     */
    public void showUserMainScreen(){
        ScreenUtils.showUserMainScreen();
    }

    /**
     * Shows user available vehicles screen
     */
    public void showUserAvailableVehiclesScreen(){
        ScreenUtils.showUserAvailableVehiclesScreen();
    }

    /**
     * Shows user add vehicles screen
     */
    public void showUserAddVehicleScreen(){
        ScreenUtils.showUserAddVehicleScreen();
    }

    /**
     * Shows user order history screen
     */
    public void showUserOrderHistoryScreen(){
        ScreenUtils.showUserOrderHistoryScreen();
    }
}
