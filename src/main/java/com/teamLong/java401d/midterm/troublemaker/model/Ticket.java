package com.teamLong.java401d.midterm.troublemaker.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    private String title;
    LocalDateTime createdAt;
    private short severity;
    private String summary;
    private boolean archived;
    Severity ticketLvl;

    //database relation
    @ManyToOne
    UserAccount creator;

    public Ticket(){}

    public Ticket(String title, Severity ticketLvl, UserAccount creator, String summary){
        this.createdAt = LocalDateTime.now();
        this.title = title;
        this.ticketLvl = ticketLvl;
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

    public Severity getTicketLvl() { return ticketLvl; }

    public UserAccount getCreator() {
        return creator;
    }

    public String getSummary() {
        return summary;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSeverity(short severity) {
        this.severity = severity;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
