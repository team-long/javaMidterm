package com.teamLong.java401d.midterm.troublemaker.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String title;
    LocalDateTime createdAt;
    short severity;
    String summary;
    boolean archived;

    //database relation
    @ManyToOne
    UserAccount creator;

    public Ticket(){}

    public Ticket(String title, short severity, UserAccount creator, String summary){
        this.createdAt = LocalDateTime.now();
        this.title = title;
        this.severity = severity;
        this.creator = creator;
        this.summary = summary;
        this.archived = false;
    }



    //getters & setters

    public long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getTitle() {
        return title;
    }

    public boolean isArchived() {
        return archived;
    }

    public short getSeverity() {
        return severity;
    }

    public UserAccount getCreator() {
        return creator;
    }

    public String getSummary() {
        return summary;
    }


}
