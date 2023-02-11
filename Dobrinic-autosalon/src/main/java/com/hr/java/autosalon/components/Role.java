package com.hr.java.autosalon.components;

import com.hr.java.autosalon.enums.RoleType;

/**
 * Role
 */
public abstract class Role {
    String username;

    public Role(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public abstract RoleType getRole();
}
