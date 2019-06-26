package com.teamLong.java401d.midterm.troublemaker.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    LocalDateTime createdAt;

    Severity ticketLvl;
    @ManyToOne
    UserAccount creator;
    String summary;
    boolean archived;

    //database relation
    @ManyToOne
    UserAccount creator;

    public Ticket(){}

    public Ticket(Severity ticketLvl, UserAccount creator, String summary){
        this.createdAt = LocalDateTime.now();
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

    public Severity getTicketLvl() { return ticketLvl; }

    public UserAccount getCreator() {
        return creator;
    }

    public String getSummary() {
        return summary;
    }
}
