package com.hr.java.autosalon.components;


/**
 * Car
 */
public class Car extends Vehicle{
   private Integer doorCount;
   private Equipment equipment;

    public Car(Long ID, String manufacturer, String type, String color, VehicleHistory vehicleHistory, Engine engine, Integer doorCount, Equipment equipment, Gearbox gearbox) {
        super(ID, manufacturer, type, color, vehicleHistory, engine,gearbox);
        this.doorCount = doorCount;
        this.equipment = equipment;
    }

    public Integer getDoorCount() {
        return doorCount;
    }

    public void setDoorCount(Integer doorCount) {
        this.doorCount = doorCount;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    @Override
    public String toString() {
        Engine engine = getEngine();
        if(engine instanceof DieselEngine){
            return "Car{" +
                    ", manufacturer=" + getManufacturer() +
                    ", type=" + getType() +
                    ", color=" + getColor() +
                    "doorCount=" + doorCount +
                    getVehicleHistory().toString() +
                    engine.toString() +
                    getEquipment().toString() +
                    getGearbox().toString() +
                    '}';
        }
        else if(engine instanceof PetrolEngine){
            return "Car{" +
                    ", manufacturer=" + getManufacturer() +
                    ", type=" + getType() +
                    ", color=" + getColor() +
                    "doorCount=" + doorCount +
                    getVehicleHistory().toString() +
                    engine.toString() +
                    getEquipment().toString() +
                    getGearbox().toString() +
                    '}';
        }
        else if(engine instanceof HybridEngine){
            return "Car{" +
                    ", manufacturer=" + getManufacturer() +
                    ", type=" + getType() +
                    ", color=" + getColor() +
                    "doorCount=" + doorCount +
                    getVehicleHistory().toString() +
                    engine.toString() +
                    getEquipment().toString() +
                    getGearbox().toString() +
                    '}';
        }
        return null;
    }
}
