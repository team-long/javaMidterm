package com.teamLong.java401d.midterm.troublemaker.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    LocalDateTime createdAt;
    String severity;
    @ManyToOne
    UserAccount creator;
    String summary;

    public Ticket(){}

    public Ticket(String severity, UserAccount creator, String summary){
        this.createdAt = LocalDateTime.now();
        this.severity = severity;
        this.creator = creator;
        this.summary = summary;
    }

//    database relation



    //getters & setters

    public long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getSeverity() { return severity; }

    public UserAccount getCreator() {
        return creator;
    }

    public String getSummary() {
        return summary;
    }
}
