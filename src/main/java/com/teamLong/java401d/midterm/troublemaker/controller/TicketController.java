package com.teamLong.java401d.midterm.troublemaker.controller;

import com.teamLong.java401d.midterm.troublemaker.model.Ticket;
import com.teamLong.java401d.midterm.troublemaker.model.UserAccount;
import com.teamLong.java401d.midterm.troublemaker.repository.TicketRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        Ticket ticket = new Ticket(severity, user, summary);
        ticketRepository.save(ticket);
        return new RedirectView("/main");
    }

    //all tickets user route
    @GetMapping("/tickets/all")
    public String getAllTickets(Principal principal, Model model){
        UserAccount loggedInUser = userRepository.findByUsername(principal.getName());
        model.addAttribute("loggedInUser", loggedInUser);
        Iterable<Ticket> tickets = ticketRepository.findAll();
        model.addAttribute("tickets", tickets);
        return "ticket-detail";
    }

}
