package com.teamLong.java401d.midterm.troublemaker.repository;

import com.teamLong.java401d.midterm.troublemaker.model.Archive;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArchiveRepository extends CrudRepository<Archive, Long> {
    Archive findById(long id);
    List<Archive> findAllByCreatorId(long id);
}
