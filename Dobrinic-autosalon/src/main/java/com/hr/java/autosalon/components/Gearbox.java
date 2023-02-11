package com.hr.java.autosalon.components;

import com.hr.java.autosalon.enums.GearboxType;

/**
 * Gearbox
 */
public class Gearbox extends Entity implements GearboxService{

    private GearboxType gearboxType;
    private int numberOfGears;

    public Gearbox(Long ID, GearboxType gearboxType, int numberOfGears) {
        super(ID);
        this.gearboxType = gearboxType;
        this.numberOfGears = numberOfGears;
    }


    public GearboxType getGearboxType() {
        return gearboxType;
    }

    public void setGearboxType(GearboxType gearboxType) {
        this.gearboxType = gearboxType;
    }

    public int getNumberOfGears() {
        return numberOfGears;
    }

    public void setNumberOfGears(int numberOfGears) {
        this.numberOfGears = numberOfGears;
    }


    @Override
    public int getGearboxServiceInterval() {
        return 100000;
    }

    @Override
    public String toString() {
        return "Gearbox{" +
                "gearboxType=" + gearboxType +
                ", numberOfGears=" + numberOfGears +
                "}\n";
    }
}
