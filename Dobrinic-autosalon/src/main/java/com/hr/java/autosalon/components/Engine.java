package com.hr.java.autosalon.components;

/**
 * Engine
 */
public abstract class Engine extends Entity {
    private Integer mileage;

    public Engine(Long ID, Integer mileage) {
        super(ID);
        this.mileage = mileage;
    }

    public abstract void sound();

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

}
