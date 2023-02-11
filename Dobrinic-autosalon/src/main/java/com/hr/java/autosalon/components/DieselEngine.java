package com.hr.java.autosalon.components;

import com.hr.java.autosalon.enums.FuelType;

/**
 * Diesel Engine
 */
public final class DieselEngine extends Engine implements EngineService{

    private Double fuelConsumption;
    private final FuelType fuelType = FuelType.DIESEL;
    private Double horsepower;

    public DieselEngine(Long ID, Integer mileage, Double fuelConsumption, Double horsepower) {
        super(ID, mileage);
        this.fuelConsumption = fuelConsumption;
        this.horsepower = horsepower;
    }

    public Double getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(Double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public Double getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(Double horsepower) {
        this.horsepower = horsepower;
    }

    @Override
    public void sound(){
        //plays an engine sound
        System.out.println("diesel engine sound");
    }

    @Override
    public int getMaxServiceIntervalInMonths() {
        return 12;
    }

    @Override
    public String toString() {
        return "DieselEngine{" +
                "fuelConsumption=" + fuelConsumption +
                ", fuelType=" + fuelType +
                ", horsepower=" + horsepower +
                "}\n";
    }
}
