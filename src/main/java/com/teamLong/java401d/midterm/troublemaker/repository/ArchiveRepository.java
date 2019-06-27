package com.teamLong.java401d.midterm.troublemaker.repository;

import com.teamLong.java401d.midterm.troublemaker.model.Archive;
import com.teamLong.java401d.midterm.troublemaker.model.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface ArchiveRepository extends CrudRepository<Archive, Long> {
    Archive findById(long id);
}
