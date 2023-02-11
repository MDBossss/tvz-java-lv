package com.hr.java.autosalon.components;

import com.hr.java.autosalon.enums.RoleType;

/**
 * User role
 */
public class User extends Role{
    public User(String username) {
        super(username);
    }

    @Override
    public RoleType getRole() {
        return RoleType.USER;
    }
}
