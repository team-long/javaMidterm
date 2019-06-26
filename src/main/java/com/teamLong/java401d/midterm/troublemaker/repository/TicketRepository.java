package com.teamLong.java401d.midterm.troublemaker.repository;

import com.teamLong.java401d.midterm.troublemaker.model.Ticket;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
    Ticket findById(long id);
    List<Ticket> findAllByCreatorId(long id);
}
