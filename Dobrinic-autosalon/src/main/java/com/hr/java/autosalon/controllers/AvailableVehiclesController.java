package com.hr.java.autosalon.controllers;


import com.hr.java.autosalon.components.*;
import com.hr.java.autosalon.enums.Condition;
import com.hr.java.autosalon.enums.FuelType;
import com.hr.java.autosalon.enums.GearboxType;
import com.hr.java.autosalon.exceptions.DatabaseException;
import com.hr.java.autosalon.main.Main;
import com.hr.java.autosalon.util.AlertUtils;
import com.hr.java.autosalon.util.Database;
import com.hr.java.autosalon.util.FilterUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Controls available vehicles screen
 */
public class AvailableVehiclesController {

    @FXML
    private TextField manufacturerTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextField colorTextField;

    @FXML
    private ChoiceBox<Condition> conditionChoiceBox;

    @FXML
    private TextField firstRegistrationDateTextField;

    @FXML
    private TextField mileageTextField;

    @FXML
    private ChoiceBox<FuelType> fuelTypeChoiceBox;

    @FXML
    private TextField fuelConsumptionTextField;

    @FXML
    private TextField horsepowerTextField;

    @FXML
    private TextField numberOfDoorsTextField;

    @FXML
    private ChoiceBox<GearboxType> gearboxChoiceBox;

    @FXML
    private CheckBox leatherCheckBox;

    @FXML
    private CheckBox airConditioningCheckBox;

    @FXML
    private CheckBox parkingAssistCheckBox;

    @FXML
    private CheckBox bluetoothCheckBox;

    @FXML
    private CheckBox ambientLightsCheckBox;

    @FXML
    private CheckBox shiftPaddlesCheckBox;

    @FXML
    private CheckBox voiceControlCheckBox;

    @FXML
    private TableView<Car> carTableView;

    @FXML
    private TableColumn<Car,String> manufacturerTableColumn;

    @FXML
    private TableColumn<Car,String> typeTableColumn;

    @FXML
    private TableColumn<Car,String> colorTableColumn;

    @FXML
    private TableColumn<Car,String> conditionTableColumn;

    @FXML
    private TableColumn<Car,String> firstRegistrationDateTableColumn;

    @FXML
    private TableColumn<Car,String> mileageTableColumn;

    @FXML
    private TableColumn<Car,String> fuelTypeTableColumn;

    @FXML
    private TableColumn<Car,String> fuelConsumptionTableColumn;

    @FXML
    private TableColumn<Car,String> horsepowerTableColumn;

    @FXML
    private TableColumn<Car,String> numberOfDoorsTableColumn;

    @FXML
    private TableColumn<Car,String> equipmentTableColumn;

    @FXML
    private TableColumn<Car,String> gearboxTableColumn;

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    Map<Long,Car> carMap = new HashMap<>();

    public void initialize(){
        try{
            carMap = Database.getCar();
        }catch (DatabaseException e){
            logger.info(e.getMessage(),e);
        }

        mileageTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEngine().getMileage().toString()));
        typeTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        colorTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getColor()));
        manufacturerTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getManufacturer()));
        conditionTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVehicleHistory().condition().value));
        firstRegistrationDateTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getVehicleHistory().firstRegistrationDate().getYear())));

        fuelTypeTableColumn.setCellValueFactory(cellData -> {
            Engine engine = cellData.getValue().getEngine();
            if(engine instanceof PetrolEngine){
                return new SimpleStringProperty(((PetrolEngine)engine).getFuelType().value);
            }
            else if(engine instanceof DieselEngine){
                return new SimpleStringProperty(((DieselEngine)engine).getFuelType().value);
            }
            else if(engine instanceof HybridEngine){
                return new SimpleStringProperty(((HybridEngine)engine).getFuelType().value);
            }
            else{
                return new SimpleStringProperty("");
            }
        });

        fuelConsumptionTableColumn.setCellValueFactory(cellData -> {
            Engine engine = cellData.getValue().getEngine();
            if(engine instanceof DieselEngine){
                return new SimpleStringProperty(((DieselEngine)engine).getFuelConsumption().toString());
            }
            else if(engine instanceof PetrolEngine){
                return new SimpleStringProperty(((PetrolEngine)engine).getFuelConsumption().toString());
            }
            else if(engine instanceof HybridEngine){
                return new SimpleStringProperty(((HybridEngine)engine).getFuelConsumption().toString());
            }
            else{
                return new SimpleStringProperty("");
            }
        });

        horsepowerTableColumn.setCellValueFactory(cellData -> {
            Engine engine = cellData.getValue().getEngine();
            if(engine instanceof DieselEngine){
                return new SimpleStringProperty(((DieselEngine)engine).getHorsepower().toString());
            }
            else if(engine instanceof PetrolEngine){
                return new SimpleStringProperty(((PetrolEngine)engine).getHorsepower().toString());
            }
            else if(engine instanceof HybridEngine){
                return new SimpleStringProperty(((HybridEngine)engine).getHorsepower().toString());
            }
            else{
                return new SimpleStringProperty("");
            }
        });

        numberOfDoorsTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDoorCount().toString()));
        equipmentTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEquipment().getListofEquipment()));
        gearboxTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGearbox().getGearboxType().value + "," + cellData.getValue().getGearbox().getNumberOfGears()));

        carTableView.setItems(FXCollections.observableArrayList(carMap.values()));

        conditionChoiceBox.setItems(FXCollections.observableArrayList(Condition.values()));
        fuelTypeChoiceBox.setItems(FXCollections.observableArrayList(FuelType.values()));
        gearboxChoiceBox.setItems(FXCollections.observableArrayList(GearboxType.values()));

    }


    /**
     * Filters the table data
     */
    public void filterData(){
        if(AlertUtils.confirmAction()){
            String manufacturer = manufacturerTextField.getText();
            String type = typeTextField.getText();
            String color = colorTextField.getText();
            Condition condition = conditionChoiceBox.getValue();
            Integer year = firstRegistrationDateTextField.getText().isEmpty() ? null : Integer.valueOf(firstRegistrationDateTextField.getText());
            Double mileage = mileageTextField.getText().isEmpty() ? null : Double.valueOf(mileageTextField.getText());
            FuelType fuelType = fuelTypeChoiceBox.getValue();
            Double fuelConsumption = fuelConsumptionTextField.getText().isEmpty() ? null : Double.valueOf(fuelConsumptionTextField.getText());
            Double horsepower = horsepowerTextField.getText().isEmpty() ? null :  Double.valueOf(horsepowerTextField.getText());
            Integer doorCount = numberOfDoorsTextField.getText().isEmpty() ? null : Integer.valueOf(numberOfDoorsTextField.getText());
            GearboxType gearboxType = gearboxChoiceBox.getValue();



            List<Car> filteredCars = carMap.values().stream()
                    .filter(car -> car.getManufacturer().contains(manufacturer))
                    .filter(car -> car.getType().contains(type))
                    .filter(car -> car.getColor().contains(color))
                    .filter(car -> condition == null || car.getVehicleHistory().condition() == condition)
                    .filter(car -> year == null || car.getVehicleHistory().firstRegistrationDate().getYear() == year)
                    .filter(car -> mileage == null || car.getEngine().getMileage() <= mileage)
                    .filter(car -> fuelType == null || FilterUtils.checkIfSameFuelType(car.getEngine(),fuelType))
                    .filter(car -> fuelConsumption == null || FilterUtils.checkIfFuelConsumptionLessThan(car.getEngine(),fuelConsumption))
                    .filter(car -> horsepower == null || FilterUtils.checkIfHorsepowerLessThan(car.getEngine(),horsepower))
                    .filter(car -> doorCount == null || Objects.equals(car.getDoorCount(), doorCount))
                    .filter(car -> gearboxType == null || car.getGearbox().getGearboxType() == gearboxType)
                    .filter(car -> !leatherCheckBox.isSelected() || car.getEquipment().isLeather() == leatherCheckBox.isSelected())
                    .filter(car -> !airConditioningCheckBox.isSelected() || car.getEquipment().isAirConditioning() == airConditioningCheckBox.isSelected())
                    .filter(car -> !parkingAssistCheckBox.isSelected() || car.getEquipment().isParkingAssist() == parkingAssistCheckBox.isSelected())
                    .filter(car -> !bluetoothCheckBox.isSelected() || car.getEquipment().isBluetooth() == bluetoothCheckBox.isSelected())
                    .filter(car -> !ambientLightsCheckBox.isSelected() || car.getEquipment().isAmbientLights() == ambientLightsCheckBox.isSelected())
                    .filter(car -> !shiftPaddlesCheckBox.isSelected() || car.getEquipment().isShiftPaddles() == shiftPaddlesCheckBox.isSelected())
                    .filter(car -> !voiceControlCheckBox.isSelected() || car.getEquipment().isVoiceControl() == voiceControlCheckBox.isSelected())
                    .toList();

            carTableView.setItems(FXCollections.observableArrayList(filteredCars));
        }
    }



}
