package com.teamLong.java401d.midterm.troublemaker.controller;

import com.teamLong.java401d.midterm.troublemaker.model.UserAccount;
import com.teamLong.java401d.midterm.troublemaker.repository.RoleRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.UserRepository;
import com.teamLong.java401d.midterm.troublemaker.email.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.util.ArrayList;
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
        if(binding.hasErrors() || !user.getConfirmPassword().equals(user.getPassword())) {
            List<String> customErrors = new ArrayList<>();
            binding.getAllErrors().forEach(error -> customErrors.add(error.getDefaultMessage()));
            if(!user.getConfirmPassword().equals(user.getPassword())) {
                customErrors.add("Password must match.");
            }
            redirect.addFlashAttribute("errors", customErrors);
            return "redirect:/login#registration";
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.getRoleTypes().add(roleRepository.findByRole("user"));
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        EmailSender.sendEmail(user, null, "INTRO", null);
        return "redirect:/main";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String indexError(@RequestParam(value = "error", required=false) boolean error, RedirectAttributes redirect) {
        if(error) {
            redirect.addFlashAttribute("error", "Incorrect Credentials");
            return "redirect:/login#registration";
        }
        return "login";
    }


}
