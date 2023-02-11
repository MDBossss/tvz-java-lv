package com.hr.java.autosalon.util;

import com.hr.java.autosalon.components.*;
import com.hr.java.autosalon.enums.Condition;
import com.hr.java.autosalon.enums.FuelType;
import com.hr.java.autosalon.enums.GearboxType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * Filtering utilities
 */
public class FilterUtils {

    /**
     * Checks if the engine is same fuel type as passed fuel type
     * @param engine car engine
     * @param fuelType fuel type
     * @return true if the fuel type is the same
     */
    public static boolean checkIfSameFuelType(Engine engine, FuelType fuelType){
        if(engine instanceof DieselEngine){
            return ((DieselEngine) engine).getFuelType() == fuelType;
        }
        else if(engine instanceof PetrolEngine){
            return ((PetrolEngine) engine).getFuelType() == fuelType;
        }
        else if(engine instanceof HybridEngine){
            return ((HybridEngine) engine).getFuelType() == fuelType;
        }
        return false;
    }

    /**
     * Checks if the engine has less or equal fuel consumption as passed fuel consumption
     * @param engine engine
     * @param fuelConsumption fuel consumption
     * @return true is fuel consumption is same or lower
     */
    public static boolean checkIfFuelConsumptionLessThan(Engine engine,Double fuelConsumption){
        if(engine instanceof DieselEngine){
            return ((DieselEngine) engine).getFuelConsumption() <= fuelConsumption;
        }
        else if(engine instanceof PetrolEngine){
            return ((PetrolEngine) engine).getFuelConsumption() <= fuelConsumption;
        }
        else if(engine instanceof HybridEngine){
            return ((HybridEngine) engine).getFuelConsumption() <= fuelConsumption;
        }
        return false;
    }

    /**
     * Checks if the engine horsepower is less or equal as passed horsepower
     * @param engine engine
     * @param horsepower horsepower
     * @return true if horsepower less or equal
     */
    public static boolean checkIfHorsepowerLessThan(Engine engine,Double horsepower){
        if(engine instanceof DieselEngine){
            return ((DieselEngine) engine).getHorsepower() <= horsepower;
        }
        else if(engine instanceof PetrolEngine){
            return ((PetrolEngine) engine).getHorsepower() <= horsepower;
        }
        else if(engine instanceof HybridEngine){
            return ((HybridEngine) engine).getHorsepower() <= horsepower;
        }
        return false;
    }

    /**
     * Checks if all the required input fields are filled
     * @return false if all filled
     */
    public static boolean checkIfInputInvalid(TextField manufacturerTextField, TextField typeTextField, TextField colorTextField, ChoiceBox<Condition> conditionChoiceBox, DatePicker firstRegistrationDatePicker, TextField mileageTextField, ChoiceBox<FuelType> fuelTypeChoiceBox, TextField fuelConsumptionTextField, TextField horsepowerTextField, TextField numberOfGearsTextField, ChoiceBox<GearboxType> gearboxChoiceBox, TextField numberOfDoorsTextField) {
        boolean invalidInput = false;

        if(manufacturerTextField.getText().isEmpty()){
            invalidInput = true;
        }

        if(typeTextField.getText().isEmpty()){
            invalidInput = true;
        }

        if(colorTextField.getText().isEmpty()){
            invalidInput = true;
        }

        if(conditionChoiceBox.getValue() == null){
            invalidInput = true;
        }

        if(firstRegistrationDatePicker.getValue() == null){
            invalidInput = true;
        }

        if(mileageTextField.getText().isEmpty()){
            invalidInput = true;
        }

        if(fuelTypeChoiceBox.getValue() == null){
            invalidInput = true;
        }

        if(gearboxChoiceBox.getValue() == null){
            invalidInput = true;
        }

        if(fuelConsumptionTextField.getText().isEmpty()){
            invalidInput = true;
        }

        if(horsepowerTextField.getText().isEmpty()){
            invalidInput = true;
        }

        if(numberOfGearsTextField.getText().isEmpty()){
            invalidInput = true;
        }


        if(numberOfDoorsTextField.getText().isEmpty()){
            invalidInput = true;
        }

        return invalidInput;
    }

}
