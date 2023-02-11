package com.hr.java.autosalon.threads;

import com.hr.java.autosalon.components.Car;
import com.hr.java.autosalon.controllers.HomeController;
import com.hr.java.autosalon.exceptions.DatabaseException;
import com.hr.java.autosalon.main.Main;
import com.hr.java.autosalon.util.Database;
import com.hr.java.autosalon.util.LoginDetails;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class StatsBarThread implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private Label numberOfAvailableVehiclesLabel;
    private Label numberOfOrdersLabel;
    private Label mostPopularManufacturerLabel;
    private Label numberOfUserOrdersLabel;


    public StatsBarThread(Label numberOfAvailableVehiclesLabel, Label numberOfOrdersLabel, Label mostPopularManufacturerLabel, Label numberOfUserOrdersLabel) {
        this.numberOfAvailableVehiclesLabel = numberOfAvailableVehiclesLabel;
        this.numberOfOrdersLabel = numberOfOrdersLabel;
        this.mostPopularManufacturerLabel = mostPopularManufacturerLabel;
        this.numberOfUserOrdersLabel = numberOfUserOrdersLabel;
    }

    @Override
    public void run() {
        numberOfAvailableVehiclesLabel.setText(getNumberOfAvailableVehicles());
        numberOfOrdersLabel.setText(getNumberOfOrders());
        mostPopularManufacturerLabel.setText(getMostPopularManufacturer());
        numberOfUserOrdersLabel.setText(getLastOrderManufacturer());
    }


    /**
     * Shows that thread works
     */
    public void demonstration(){
        List<String> list = new ArrayList<>();
        Random random = new Random();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        numberOfAvailableVehiclesLabel.setText(list.get(random.nextInt(list.size())));
        numberOfOrdersLabel.setText(list.get(random.nextInt(list.size())));
        mostPopularManufacturerLabel.setText(list.get(random.nextInt(list.size())));
        numberOfUserOrdersLabel.setText(list.get(random.nextInt(list.size())));
    }

    /**
     * Gets the number of available vehicles in database
     * @return number of vehicles
     */
    public String getNumberOfAvailableVehicles(){
        try{
            return String.valueOf(Database.getCar().values().size());
        }catch (DatabaseException e){
            logger.info(e.getMessage(),e);
        }
        return "0";
    }

    /**
     * Gets the number of orders from all users in database
     * @return number of orders
     */
    public String getNumberOfOrders(){
        try{
            return String.valueOf(Database.getUserCar().values().size());
        }catch (DatabaseException e){
            logger.info(e.getMessage(),e);
        }
        return "0";
    }

    /**
     * Gets the most popular manufacturer from available vehicles database
     * @return most common manufacturer name
     */
    public String getMostPopularManufacturer(){
        Map<Long,Car> carMap = new HashMap<>();
        Map<String,Integer> manufacturerCount = new HashMap<>();

        try{
            carMap = Database.getCar();
        }catch (DatabaseException e){
            logger.info(e.getMessage(),e);
        }

        carMap.values().forEach(car -> {
            manufacturerCount.put(car.getManufacturer(),manufacturerCount.getOrDefault(car.getManufacturer(),0) + 1);
        });

        String mostCommonManufacturer = null;
        int maxCount = Integer.MIN_VALUE;
        for(Map.Entry<String,Integer> entry : manufacturerCount.entrySet()){
            if(entry.getValue() > maxCount){
                maxCount = entry.getValue();
                mostCommonManufacturer = entry.getKey();
            }
        }
        return mostCommonManufacturer;
    }

    /**
     * Gets the number of orders from currently logged-in user
     * @return number of orders
     */
    public String getLastOrderManufacturer(){
        Map<Long,Car> carMap = new HashMap<>();
        try{
            carMap = Database.getUserCar();
        }catch (DatabaseException e){
            logger.info(e.getMessage(),e);
        }
        if(carMap.values().isEmpty()){
            return "Nemate narudžbi!";
        }
        else{
            return carMap.values().stream().toList().get(0).getManufacturer();
        }
    }
}
