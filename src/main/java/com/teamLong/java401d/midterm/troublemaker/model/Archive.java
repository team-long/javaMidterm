package com.teamLong.java401d.midterm.troublemaker.model;

import javax.persistence.*;
import javax.print.attribute.standard.Severity;
import java.lang.reflect.Array;
import java.time.LocalDateTime;

@Entity
public class Archive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String title;
    LocalDateTime createdAt;
    Severity ticketLvl;
    private String summary;
    private boolean archived;

    //database relation
    @ManyToOne
    UserAccount creator;

    public Archive(){}

    public Archive(String title, Severity ticketLvl, UserAccount creator, String summary){
        this.createdAt = LocalDateTime.now();
        this.title = title;
        this.ticketLvl = ticketLvl;
        this.creator = creator;
        this.summary = summary;
        this.archived = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Severity getTicketLvl() {
        return ticketLvl;
    }

    public void setTicketLvl(Severity ticketLvl) {
        this.ticketLvl = ticketLvl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public UserAccount getCreator() {
        return creator;
    }

    public void setCreator(UserAccount creator) {
        this.creator = creator;
    }
}
