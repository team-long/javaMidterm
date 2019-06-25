package com.teamLong.java401d.midterm.troublemaker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class TicketController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    @GetMapping("/ticket")
    public String createTicketPage(){
        return "ticket";
    }

    @PostMapping("/create/ticket")
    public RedirectView makeATicket(String summary, short severity, Principal p){
        UserAccount user = userRepository.findByUsername(p.getName());
        Ticket ticket = new Ticket(summary, severity, user);
        ticketRepository.save(ticket);
        return new RedirectView("/profile");
    }
}
