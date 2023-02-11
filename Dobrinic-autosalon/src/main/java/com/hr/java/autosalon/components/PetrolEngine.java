package com.hr.java.autosalon.components;

import com.hr.java.autosalon.enums.FuelType;

/**
 * Petrol Engine
 */
public final class PetrolEngine extends Engine implements EngineService{
    private Double fuelConsumption;
    private final FuelType fuelType = FuelType.PETROL;
    private Double horsepower;

    public PetrolEngine(Long ID, Integer mileage, Double fuelConsumption, Double horsepower) {
        super(ID, mileage);
        this.fuelConsumption = fuelConsumption;
        this.horsepower = horsepower;
    }

    public Double getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(Double horsepower) {
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


    @Override
    public void sound() {
        //plays an engine sound
        System.out.println("petrol engine sound");
    }

    @Override
    public int getMaxServiceIntervalInMonths() {
        return 12;
    }

    @Override
    public String toString() {
        return "PetrolEngine{" +
                "fuelConsumption=" + fuelConsumption +
                ", fuelType=" + fuelType +
                ", horsepower=" + horsepower +
                "}\n";
    }
}
