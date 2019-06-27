package com.teamLong.java401d.midterm.troublemaker.controller;

import com.teamLong.java401d.midterm.troublemaker.model.RoleType;
import com.teamLong.java401d.midterm.troublemaker.model.UserAccount;
import com.teamLong.java401d.midterm.troublemaker.repository.RoleRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String user(Principal user, Model m) {
        Iterable<UserAccount> userList = userRepository.findAll();
        m.addAttribute("principal", userRepository.findByUsername(user.getName()));
        m.addAttribute("users", userList);
        return "user";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String makeAdmin(Principal user, Model m, String makeUserAdmin) {
        if(userRepository.findByUsername(user.getName()).isAdmin()) {
            UserAccount selectedUser = userRepository.findByUsername(makeUserAdmin);
            RoleType adminRole = roleRepository.findByRole("admin");
            selectedUser.getRoleTypes().add(adminRole);
            userRepository.save(selectedUser);
        }
        return "redirect:/profile";
    }
}
