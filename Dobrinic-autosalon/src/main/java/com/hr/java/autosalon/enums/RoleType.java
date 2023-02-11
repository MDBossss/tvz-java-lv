package com.hr.java.autosalon.enums;

/**
 * Role type enum
 */
public enum RoleType {
    ADMIN("ADMIN"),
    USER("USER");

    public final String value;

    RoleType(String value){
        this.value = value;
    }

    /**
     * Parses role type of string
     * @param value string value of role type
     * @return parsed role type
     */
    public static RoleType parseRoleType(String value){
        return switch(value){
            case "ADMIN" -> ADMIN;
            case "USER" -> USER;
            default -> {
                String message = "Invalid RoleType value: " + value;
                throw new RuntimeException(message);
            }
        };
    }
}
