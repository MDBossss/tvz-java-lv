package com.hr.java.autosalon.util;

/**
 * Generic class that takes a role
 * @param <T> user with a role
 */
public class LoginDetails<T> {

    T user;
    boolean isValid;

    public LoginDetails(T user, boolean isValid) {
        this.user = user;
        this.isValid = isValid;
    }

    public T getUser() {
        return user;
    }

    public void setUser(T user) {
        this.user = user;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
