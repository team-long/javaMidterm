package com.teamLong.java401d.midterm.troublemaker.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.teamLong.java401d.midterm.troublemaker.model.Ticket;
import com.teamLong.java401d.midterm.troublemaker.model.UserAccount;
import com.teamLong.java401d.midterm.troublemaker.repository.TicketRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class TicketController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    @GetMapping("/create/ticket")
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
        return "allTickets";
    }

    @GetMapping("/edit/{id}")
    public String editTicket(@PathVariable long id, Principal principal, Model model){
        Ticket ticket = ticketRepository.findById(id);
        model.addAttribute("ticket", ticket);
        UserAccount loggedInUser = userRepository.findByUsername(principal.getName());
        model.addAttribute("loggedInUser", loggedInUser);
        return "edit";
    }

//    @PutMapping("/tickets/edit/{id}")
//    public RedirectView updateTicket(@ModelAttribute("ticket") Ticket ticket){
//        ticketRepository.update(ticket);
//        return new RedirectView("allTickets");
//    }

    @DeleteMapping("delete/ticket/{id}")
    public RedirectView deleteTicket(@PathVariable long id, Principal principal, Model model){
        Ticket ticket = ticketRepository.findById(id);
        if(ticket.getCreator().equals(principal.getName())){
            ticketRepository.deleteById(id);
        } else {
            throw new TicketDoesNotBelongToYou("There is only one thing we say to death. Not today.\n You do not own this ticket");
        }
        return new RedirectView("allTickets");
    }

}

@ResponseStatus(value = HttpStatus.FORBIDDEN)
class TicketDoesNotBelongToYou extends RuntimeException {
    public TicketDoesNotBelongToYou(String string){
        super(string);
    }
}
