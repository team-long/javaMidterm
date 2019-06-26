package com.teamLong.java401d.midterm.troublemaker.controller;

import com.teamLong.java401d.midterm.troublemaker.model.Ticket;
import com.teamLong.java401d.midterm.troublemaker.model.UserAccount;
import com.teamLong.java401d.midterm.troublemaker.repository.TicketRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.print.attribute.standard.Severity;
import java.security.Principal;
import java.util.Optional;

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

    @GetMapping("/ticket/{ticketId}")
    public String getOneTicketPage(@PathVariable long ticketId, Model m ){
        Ticket oneTicket = ticketRepository.findById(ticketId);
        m.addAttribute("ticket", oneTicket);
        return "ticket-detail";
    }

    @PostMapping("/create/ticket")
    public RedirectView makeATicket(String summary, String severity, Principal p){
        UserAccount user = userRepository.findByUsername(p.getName());
        Ticket ticket = new Ticket(severity, user, summary);
        ticketRepository.save(new Ticket(severity, user, summary));
        return new RedirectView("/profile");
    }


    //all tickets user route
}
