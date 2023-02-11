package com.hr.java.autosalon.util;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Generic class that stores changes
 * @param <T1> before object
 * @param <T2> after object
 */
public class Change<T1,T2> implements Serializable {
    private T1 before;
    private T2 after;
    private String username;
    private LocalDateTime timestamp;

    public Change(T1 before, T2 after, String username, LocalDateTime timestamp) {
        this.before = before;
        this.after = after;
        this.username = username;
        this.timestamp = timestamp;
    }

    public T1 getBefore() {
        return before;
    }

    public T2 getAfter() {
        return after;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
