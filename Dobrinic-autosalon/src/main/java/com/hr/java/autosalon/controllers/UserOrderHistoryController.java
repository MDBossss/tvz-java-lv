package com.hr.java.autosalon.controllers;

import com.hr.java.autosalon.components.*;
import com.hr.java.autosalon.enums.Condition;
import com.hr.java.autosalon.enums.FuelType;
import com.hr.java.autosalon.enums.GearboxType;
import com.hr.java.autosalon.components.Car;
import com.hr.java.autosalon.exceptions.ChangesException;
import com.hr.java.autosalon.exceptions.DatabaseException;
import com.hr.java.autosalon.exceptions.NotAllFieldsFilledException;
import com.hr.java.autosalon.main.Main;
import com.hr.java.autosalon.util.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Controls the user order history view
 */
public class UserOrderHistoryController {

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

    @FXML
    private Label successLabel;

    @FXML
    private Label failedLabel;

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    Map<Long,Car> carMap = new HashMap<>();
    Car selectedCar = null;

    public void initialize(){
        try{
            carMap = Database.getUserCar();
        }catch (DatabaseException e){
            logger.info(e.getMessage(),e);
        }


        carTableView.setRowFactory(tv -> {
            TableRow<Car> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (! row.isEmpty())){
                    selectedCar = row.getItem();
                    updateInputs(selectedCar);
                }
            });
            return row;
        });

        manufacturerTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getManufacturer()));
        typeTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        colorTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getColor()));
        conditionTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVehicleHistory().condition().value));
        firstRegistrationDateTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getVehicleHistory().firstRegistrationDate().getYear())));
        mileageTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEngine().getMileage().toString()));

        fuelTypeTableColumn.setCellValueFactory(cellData -> {
            Engine engine = cellData.getValue().getEngine();
            if(engine instanceof DieselEngine){
                return new SimpleStringProperty(((DieselEngine)engine).getFuelType().value);
            }
            else if(engine instanceof PetrolEngine){
                return new SimpleStringProperty(((PetrolEngine)engine).getFuelType().value);
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
     * Updates the inputs of input fields
     * @param car car data to update with
     */
    public void updateInputs(Car car){
        manufacturerTextField.setText(car.getManufacturer());
        typeTextField.setText(car.getType());
        colorTextField.setText(car.getColor());
        conditionChoiceBox.setValue(car.getVehicleHistory().condition());
        firstRegistrationDatePicker.setValue(car.getVehicleHistory().firstRegistrationDate());

        if(car.getEngine() instanceof DieselEngine){
            mileageTextField.setText(car.getEngine().getMileage().toString());
            fuelTypeChoiceBox.setValue(((DieselEngine) car.getEngine()).getFuelType());
            fuelConsumptionTextField.setText(((DieselEngine) car.getEngine()).getFuelConsumption().toString());
            horsepowerTextField.setText(((DieselEngine) car.getEngine()).getHorsepower().toString());
        }
        else if(car.getEngine() instanceof PetrolEngine){
            mileageTextField.setText(car.getEngine().getMileage().toString());
            fuelTypeChoiceBox.setValue(((PetrolEngine) car.getEngine()).getFuelType());
            fuelConsumptionTextField.setText(((PetrolEngine) car.getEngine()).getFuelConsumption().toString());
            horsepowerTextField.setText(((PetrolEngine) car.getEngine()).getHorsepower().toString());
        }
        else if(car.getEngine() instanceof HybridEngine){
            mileageTextField.setText(car.getEngine().getMileage().toString());
            fuelTypeChoiceBox.setValue(((HybridEngine) car.getEngine()).getFuelType());
            fuelConsumptionTextField.setText(((HybridEngine) car.getEngine()).getFuelConsumption().toString());
            horsepowerTextField.setText(((HybridEngine) car.getEngine()).getHorsepower().toString());
        }

        numberOfDoorsTextField.setText(car.getDoorCount().toString());
        gearboxChoiceBox.setValue(car.getGearbox().getGearboxType());
        numberOfGearsTextField.setText(String.valueOf((car.getGearbox().getNumberOfGears())));
        leatherCheckBox.setSelected(car.getEquipment().isLeather());
        airConditioningCheckBox.setSelected(car.getEquipment().isAirConditioning());
        parkingAssistCheckBox.setSelected(car.getEquipment().isParkingAssist());
        bluetoothCheckBox.setSelected(car.getEquipment().isBluetooth());
        ambientLightsCheckBox.setSelected(car.getEquipment().isAmbientLights());
        shiftPaddlesCheckBox.setSelected(car.getEquipment().isShiftPaddles());
        voiceControlCheckBox.setSelected(car.getEquipment().isVoiceControl());

    }

    /**
     * Deletes the selected car from database
     */
    public void deleteCar(){
        if(AlertUtils.confirmAction()){
            if(selectedCar != null){
                try{
                    Database.deleteCar(selectedCar);
                    carTableView.setItems(FXCollections.observableArrayList(Database.getUserCar().values()));
                    AlertUtils.displayMessage(successLabel,"Uspješno obrisana narudžba!",2);
                    ChangesWriter.addChange(new Change<>(selectedCar,"0",LoginUtils.getCurrentUser(),LocalDateTime.now()));
                }catch (DatabaseException | ChangesException e){
                    logger.info(e.getMessage(),e);
                }
            }
            else{
                AlertUtils.displayMessage(failedLabel,"Nije odabran auto!",2);
            }
        }
    }

    /**
     * Updates the selected car from database
     */
    public void updateCar(){
        if(AlertUtils.confirmAction()){
            if(selectedCar != null){
                try{
                    Database.updateCar(createCarFromFields());
                    carTableView.setItems(FXCollections.observableArrayList(Database.getUserCar().values()));
                    AlertUtils.displayMessage(successLabel,"Uspješno izmijenjena narudžba!",2);
                    ChangesWriter.addChange(new Change<>(selectedCar,createCarFromFields(), LoginUtils.getCurrentUser(), LocalDateTime.now()));
                }catch (DatabaseException e){
                    logger.info(e.getMessage(),e);
                }
            }
            else{
                AlertUtils.displayMessage(failedLabel,"Nije odabran auto!",2);
            }
        }
    }

    /**
     * Creates a car from input fields
     * @return created car
     */
    public Car createCarFromFields(){
        try{
            if(FilterUtils.checkIfInputInvalid(manufacturerTextField,typeTextField,colorTextField,conditionChoiceBox,firstRegistrationDatePicker,mileageTextField,fuelTypeChoiceBox,fuelConsumptionTextField,horsepowerTextField,numberOfGearsTextField,gearboxChoiceBox,numberOfDoorsTextField)){
                throw new NotAllFieldsFilledException();
            }
            else{
                Engine engine = null;
                switch(fuelTypeChoiceBox.getValue()) {
                    case DIESEL ->
                            engine = new DieselEngine(selectedCar.getEngine().getID(), Integer.valueOf(mileageTextField.getText()), Double.valueOf(fuelConsumptionTextField.getText()), Double.valueOf(horsepowerTextField.getText()));
                    case PETROL ->
                            engine = new PetrolEngine(selectedCar.getEngine().getID(), Integer.valueOf(mileageTextField.getText()), Double.valueOf(fuelConsumptionTextField.getText()), Double.valueOf(horsepowerTextField.getText()));
                    case HYBRID ->
                            engine = new HybridEngine(selectedCar.getEngine().getID(), Integer.valueOf(mileageTextField.getText()), Double.valueOf(fuelConsumptionTextField.getText()), Double.valueOf(horsepowerTextField.getText()));
                }

                Equipment.EquipmentBuilder eqb = new Equipment.EquipmentBuilder();
                eqb.addShiftPaddles(shiftPaddlesCheckBox.isSelected());
                eqb.addLeather(leatherCheckBox.isSelected());
                eqb.addAirConditioning(airConditioningCheckBox.isSelected());
                eqb.addParkingAssist(parkingAssistCheckBox.isSelected());
                eqb.addBluetooth(bluetoothCheckBox.isSelected());
                eqb.addAmbientLights(ambientLightsCheckBox.isSelected());
                eqb.addVoiceControl(voiceControlCheckBox.isSelected());
                eqb.setID(selectedCar.getEquipment().getID());
                Equipment equipment = eqb.build();


                return new Car(
                        selectedCar.getID(),
                        manufacturerTextField.getText(),
                        typeTextField.getText(),
                        colorTextField.getText(),
                        new VehicleHistory(
                                selectedCar.getVehicleHistory().ID(),
                                conditionChoiceBox.getValue(),
                                selectedCar.getVehicleHistory().ownerNumber(),
                                firstRegistrationDatePicker.getValue()),
                        engine,
                        Integer.valueOf(numberOfDoorsTextField.getText()),
                        equipment,
                        new Gearbox(
                                selectedCar.getGearbox().getID(),
                                gearboxChoiceBox.getValue(),
                                Integer.parseInt(numberOfGearsTextField.getText())
                        )
                );
            }
        }catch (NotAllFieldsFilledException e){
            e.printStackTrace();
            logger.info(e.getMessage(),e);
        }
        return null;
    }

}
