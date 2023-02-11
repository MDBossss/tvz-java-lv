package com.hr.java.autosalon.components;

import com.hr.java.autosalon.enums.Condition;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Vehicle History
 * @param ID id
 * @param condition condition of vehicle
 * @param ownerNumber number of past vehicle owners
 * @param firstRegistrationDate first registration date
 */
public record VehicleHistory(Long ID,Condition condition, Integer ownerNumber, LocalDate firstRegistrationDate) implements Serializable {
    public VehicleHistory(Long ID,Condition condition, Integer ownerNumber, LocalDate firstRegistrationDate){
        this.ID = ID;
        this.condition = condition;
        this.ownerNumber = ownerNumber;
        this.firstRegistrationDate = firstRegistrationDate;
    }

    @Override
    public Long ID() {
        return ID;
    }

    @Override
    public Condition condition() {
        return condition;
    }

    @Override
    public Integer ownerNumber() {
        return ownerNumber;
    }

    @Override
    public LocalDate firstRegistrationDate() {
        return firstRegistrationDate;
    }

    @Override
    public String toString() {
        return "VehicleHistory{" +
                "ID=" + ID +
                ", condition=" + condition +
                ", ownerNumber=" + ownerNumber +
                ", firstRegistrationDate=" + firstRegistrationDate +
                "}\n";
    }
}
