package com.teamLong.java401d.midterm.troublemaker.repository;

import com.teamLong.java401d.midterm.troublemaker.model.RoleType;
import com.teamLong.java401d.midterm.troublemaker.model.UserAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<RoleType, Long> {
    RoleType findByRole(String role);
}
