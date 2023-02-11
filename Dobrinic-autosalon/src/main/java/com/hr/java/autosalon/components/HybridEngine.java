package com.hr.java.autosalon.components;

import com.hr.java.autosalon.enums.FuelType;

/**
 * Hybrid Engine
 */
public final class HybridEngine extends Engine implements EngineService{
    private Double fuelConsumption;
    private final FuelType fuelType = FuelType.HYBRID;
    private Double horsepower;


    public HybridEngine(Long ID, Integer mileage, Double fuelConsumption, Double horsepower) {
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
        System.out.println("hybrid engine sound");
    }

    @Override
    public int getMaxServiceIntervalInMonths() {
        return 12;
    }

    @Override
    public String toString() {
        return "HybridEngine{" +
                "fuelConsumption=" + fuelConsumption +
                ", fuelType=" + fuelType +
                ", horsepower=" + horsepower +
                "}\n";
    }
}
