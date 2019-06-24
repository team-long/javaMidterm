package com.teamLong.java401d.midterm.troublemaker.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="roleTypes")
public class RoleType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roleTypes")
    private List<UserAccount> userAccounts;

}
