package com.hr.java.autosalon.enums;

/**
 * Fuel type of engine enum
 */
public enum FuelType {
    PETROL("Petrol"),
    DIESEL("Diesel"),
    HYBRID("Hybrid");

    public final String value;
    FuelType(String value){
        this.value  = value;
    }

    /**
     * Parses fuel type
     * @param value string value of fuel type
     * @return parsed fuel type
     */
    public static FuelType parseFuelType(String value){
        return switch(value){
            case "Petrol" -> PETROL;
            case "Diesel" -> DIESEL;
            case "Hybrid" -> HYBRID;
            default -> {
                String message = "Invalid fuel type value: " + value;
                throw new RuntimeException(message);
            }
        };
    }
}
