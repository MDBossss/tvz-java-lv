package com.hr.java.autosalon.components;

import com.hr.java.autosalon.enums.RoleType;

/**
 * Admin role
 */
public class Admin extends Role{

    public Admin(String username) {
        super(username);
    }

    @Override
    public RoleType getRole() {
        return RoleType.ADMIN;
    }
}
