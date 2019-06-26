package com.teamLong.java401d.midterm.troublemaker.controller;

import com.teamLong.java401d.midterm.troublemaker.model.RoleType;
import com.teamLong.java401d.midterm.troublemaker.model.UserAccount;
import com.teamLong.java401d.midterm.troublemaker.repository.RoleRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class UserAccountController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model m) {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String createUser(@Valid @ModelAttribute UserAccount user, BindingResult binding, RedirectAttributes redirect) {
        if(binding.hasErrors()) {
            redirect.addFlashAttribute("errors", binding.getAllErrors());
            return "redirect:/login";
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.getRoleTypes().add(roleRepository.findByRole("user"));
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/user";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String index() {
        return "login";
    }
}
