package com.teamLong.java401d.midterm.troublemaker.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    LocalDateTime createdAt;
    short severity;
    String creator;
    String summary;

    public Ticket(){}

    public Ticket(short severity, String creator, String summary){
        this.createdAt = LocalDateTime.now();
        this.severity = severity;
        this.creator = creator;
        this.summary = summary;
    }

    //database relation
//    @ManyToOne
//    UserAccount creator;

    //getters & setters

    public long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public short getSeverity() {
        return severity;
    }

    public String getCreator() {
        return creator;
    }

    public String getSummary() {
        return summary;
    }
}
