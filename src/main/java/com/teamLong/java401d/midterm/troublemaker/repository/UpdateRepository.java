package com.teamLong.java401d.midterm.troublemaker.repository;

import com.teamLong.java401d.midterm.troublemaker.model.Update;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UpdateRepository extends CrudRepository<Update, Long> {
    Update findById(long id);
    List<Update> findAllByTicketId(long id);
}
