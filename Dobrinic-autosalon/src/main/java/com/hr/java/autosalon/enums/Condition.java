package com.hr.java.autosalon.enums;

/**
 * Condition of vehicle enum
 */
public enum Condition {
    USED("USED"),
    NEW("NEW");

    public final String value;

    Condition(String value){
        this.value = value;
    }

    /**
     * Parses condition
     * @param value string value of condition
     * @return parsed condition
     */
    public static Condition parseCondition(String value){
        return switch(value){
            case "USED" -> USED;
            case "NEW" -> NEW;
            default -> {
                String message = "Invalid condition value: " + value;
                throw new RuntimeException(message);
            }
        };
    }
}
