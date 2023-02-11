package com.hr.java.autosalon.components;

import java.io.Serializable;

/**
 * Entity
 */
public class Entity implements Serializable {
    private Long ID;

    public Entity(Long ID) {
        this.ID = ID;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
}
