package com.teamLong.java401d.midterm.troublemaker;

import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<AdminAccount, Long> {
    AdminAccount findByUsername(String username);

}
