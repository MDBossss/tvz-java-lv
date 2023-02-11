package com.hr.java.autosalon.controllers;


import com.hr.java.autosalon.components.Car;
import com.hr.java.autosalon.exceptions.DatabaseException;
import com.hr.java.autosalon.main.Main;
import com.hr.java.autosalon.threads.StatsBarThread;
import com.hr.java.autosalon.util.Database;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Controls the home view
 */
public class HomeController {

    @FXML
    public Label numberOfAvailableVehiclesLabel;

    @FXML
    private Label numberOfOrdersLabel;

    @FXML
    private Label mostPopularManufacturerLabel;

    @FXML
    private Label numberOfUserOrdersLabel;

    @FXML
    private TableView<String> manufacturersTableView;

    @FXML
    private TableColumn<String,String> manufacturerTableColumn;

    @FXML
    private PieChart vehiclesPieChart;

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public void initialize(){

        manufacturerTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
        manufacturersTableView.setItems(FXCollections.observableArrayList(getAllManufacturersFromAvailableCars().stream().toList()));

        vehiclesPieChart.setData(FXCollections.observableArrayList(getNumberForEachManufacturer().entrySet().stream().map(e -> new PieChart.Data(e.getKey(),e.getValue())).collect(Collectors.toList())));

        Timeline updateLabels = new Timeline(
                new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Platform.runLater(new StatsBarThread(numberOfAvailableVehiclesLabel,numberOfOrdersLabel,mostPopularManufacturerLabel,numberOfUserOrdersLabel));
                    }
                }));
        updateLabels.setCycleCount(Timeline.INDEFINITE);
        updateLabels.play();

    }


    /**
     * Gets only one from each manufacturer name using a set
     * @return set of manufacturer names
     */
    public Set<String> getAllManufacturersFromAvailableCars(){
        Map<Long, Car> carMap = new HashMap<>();
        Set<String> carSet = new HashSet<>();
        try{
            carMap = Database.getCar();
        }catch (DatabaseException e){
            logger.info(e.getMessage(),e);
        }
        carMap.values().forEach(car -> {
            carSet.add(car.getManufacturer());
        });
        return carSet;
    }

    /**
     * Gets a number of how many of individual manufacturers there is
     * @return map of manufacturer name and its count
     */
    public Map<String,Integer> getNumberForEachManufacturer(){
        Map<String,Integer> manufacturerCount = new HashMap<>();
        Map<Long,Car> carMap = new HashMap<>();

        try{
            carMap = Database.getCar();
        }catch (DatabaseException e){
            logger.info(e.getMessage(),e);
        }

        carMap.values().forEach(car -> {
            manufacturerCount.put(car.getManufacturer(),manufacturerCount.getOrDefault(car.getManufacturer(),0) + 1);
        });

        return manufacturerCount;
    }


}
