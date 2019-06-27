package com.teamLong.java401d.midterm.troublemaker.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Update {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String comments;

    LocalDateTime timeStamp;

    public String getName() {
        return name;
    }

    public void setUsername(String username) {
        this.name = username;
    }

    String name;

    @ManyToOne()
    @JoinColumn()
    Ticket ticket;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public long getId() {
        return id;
    }

    public Update(){};

    public Update(String comments, Ticket ticket, String username) {
        this.comments = comments;
        this.timeStamp = LocalDateTime.now();
        this.ticket = ticket;
        this.name = username;
    }

    public String getTimeStamp() {
        return timeStamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
