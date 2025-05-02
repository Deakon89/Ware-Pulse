package com.warepulse.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Notification {
    @Id @GeneratedValue
    private Long id;

    private String message;
    private Instant timestamp;

    // getter / setter
    public Long getId() { return id; }
    public String getMessage() { return message; }
    public Instant getTimestamp() { return timestamp; }
    public void setMessage(String message) { this.message = message; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}

