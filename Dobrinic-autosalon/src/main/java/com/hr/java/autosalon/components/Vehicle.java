package com.hr.java.autosalon.components;

/**
 * Vehicle
 */
public class Vehicle extends Entity{
    private String manufacturer;
    private String type;
    private String color;
    private VehicleHistory vehicleHistory;
    private Engine engine;
    private Gearbox gearbox;

    public Vehicle(Long ID, String manufacturer, String type, String color, VehicleHistory vehicleHistory, Engine engine, Gearbox gearbox) {
        super(ID);
        this.manufacturer = manufacturer;
        this.type = type;
        this.color = color;
        this.vehicleHistory = vehicleHistory;
        this.engine = engine;
        this.gearbox = gearbox;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public VehicleHistory getVehicleHistory() {
        return vehicleHistory;
    }

    public void setVehicleHistory(VehicleHistory vehicleHistory) {
        this.vehicleHistory = vehicleHistory;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Gearbox getGearbox() {
        return gearbox;
    }

    public void setGearbox(Gearbox gearbox) {
        this.gearbox = gearbox;
    }
}
