package com.hr.java.autosalon.enums;

/**
 * Gearbox type enum
 */
public enum GearboxType {
    MANUAL("Manual"),
    AUTOMATIC("Automatic");

    public final String value;
    GearboxType(String value){
        this.value = value;
    }

    /**
     * Parses gearbox type
     * @param value string value of gearbox type
     * @return parsed gearbox type
     */
    public static GearboxType parseGearboxType(String value){
        return switch(value){
            case "Manual" -> MANUAL;
            case "Automatic" -> AUTOMATIC;
            default -> {
                String message = "Invalid gearbox type value: " + value;
                throw new RuntimeException(message);
            }
        };
    }
}
