package com.teamLong.java401d.midterm.troublemaker.repository;

import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<AdminAccount, Long> {
    AdminAccount findByUsername(String username);

}
