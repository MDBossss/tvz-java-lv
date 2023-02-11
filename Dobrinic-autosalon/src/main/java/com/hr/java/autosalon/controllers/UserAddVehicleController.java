package com.hr.java.autosalon.controllers;

import com.hr.java.autosalon.components.*;
import com.hr.java.autosalon.enums.Condition;
import com.hr.java.autosalon.enums.FuelType;
import com.hr.java.autosalon.enums.GearboxType;
import com.hr.java.autosalon.exceptions.DatabaseException;
import com.hr.java.autosalon.exceptions.NotAllFieldsFilledException;
import com.hr.java.autosalon.main.Main;
import com.hr.java.autosalon.util.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;


/**
 * Controls the user add vehicle view
 */
public class UserAddVehicleController {
    @FXML
    private TextField manufacturerTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextField colorTextField;

    @FXML
    private ChoiceBox<Condition> conditionChoiceBox;

    @FXML
    private DatePicker firstRegistrationDatePicker;

    @FXML
    private TextField ownerNumberTextField;

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
    private TextField numberOfGearsTextField;

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
    private Label successLabel;

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public void initialize(){
        conditionChoiceBox.setItems(FXCollections.observableArrayList(Condition.values()));
        fuelTypeChoiceBox.setItems(FXCollections.observableArrayList(FuelType.values()));
        gearboxChoiceBox.setItems(FXCollections.observableArrayList(GearboxType.values()));
    }

    /**
     * Adds vehicle to database
     */
    public void addVehicle(){
        try{
            if(FilterUtils.checkIfInputInvalid(manufacturerTextField,typeTextField,colorTextField,conditionChoiceBox,firstRegistrationDatePicker,mileageTextField,fuelTypeChoiceBox,fuelConsumptionTextField,horsepowerTextField,numberOfGearsTextField,gearboxChoiceBox,numberOfDoorsTextField)){
                AlertUtils.displayErrorAlert("POGREŠKA","Popunite sva polja prije dodavanja!","Nemoguće dodati vozilo!");
                throw new NotAllFieldsFilledException();
            }
            else{
                if(AlertUtils.confirmAction()){
                    Engine engine = null;
                    switch(fuelTypeChoiceBox.getValue()) {
                        case DIESEL ->
                                engine = new DieselEngine(null, Integer.valueOf(mileageTextField.getText()), Double.valueOf(fuelConsumptionTextField.getText()), Double.valueOf(horsepowerTextField.getText()));
                        case PETROL ->
                                engine = new PetrolEngine(null, Integer.valueOf(mileageTextField.getText()), Double.valueOf(fuelConsumptionTextField.getText()), Double.valueOf(horsepowerTextField.getText()));
                        case HYBRID ->
                                engine = new HybridEngine(null, Integer.valueOf(mileageTextField.getText()), Double.valueOf(fuelConsumptionTextField.getText()), Double.valueOf(horsepowerTextField.getText()));
                    }

                    Equipment.EquipmentBuilder eqb = new Equipment.EquipmentBuilder();
                    eqb.addLeather(leatherCheckBox.isSelected());
                    eqb.addAirConditioning(airConditioningCheckBox.isSelected());
                    eqb.addParkingAssist(parkingAssistCheckBox.isSelected());
                    eqb.addBluetooth(bluetoothCheckBox.isSelected());
                    eqb.addAmbientLights(ambientLightsCheckBox.isSelected());
                    eqb.addShiftPaddles(shiftPaddlesCheckBox.isSelected());
                    eqb.addVoiceControl(voiceControlCheckBox.isSelected());
                    Equipment equipment = eqb.build();



                    Car createdCar = new Car(
                            null,
                            manufacturerTextField.getText(),
                            typeTextField.getText(),
                            colorTextField.getText(),
                            new VehicleHistory(
                                    null,
                                    conditionChoiceBox.getValue(),
                                    Integer.parseInt(ownerNumberTextField.getText()),
                                    firstRegistrationDatePicker.getValue()),
                            engine,
                            Integer.valueOf(numberOfDoorsTextField.getText()),
                            equipment,
                            new Gearbox(
                                    null,
                                    gearboxChoiceBox.getValue(),
                                    Integer.parseInt(numberOfGearsTextField.getText())
                            )
                    );
                    Database.addUserCar(createdCar);
                    AlertUtils.displayMessage(successLabel,"Narudžba zaprimljena",2);
                    ChangesWriter.addChange(new Change<>("0",createdCar, LoginUtils.getCurrentUser(), LocalDateTime.now()));
                }
            }
        }catch (NotAllFieldsFilledException | DatabaseException e){
            logger.info(e.getMessage(),e);
        }
    }
}
