package com.teamLong.java401d.midterm.troublemaker;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketRepository extends CrudRepository<Tickets, Long> {
    List<Tickets> findByusername(String Username);
}
