package com.teamLong.java401d.midterm.troublemaker.controller;

import com.teamLong.java401d.midterm.troublemaker.model.Archive;
import com.teamLong.java401d.midterm.troublemaker.model.Ticket;
import com.teamLong.java401d.midterm.troublemaker.repository.ArchiveRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.TicketRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class ArchiveController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;
    
    @Autowired
    ArchiveRepository archiveRepository;

    @DeleteMapping("delete/ticket/{id}")
    public RedirectView deleteTicket(@PathVariable long id, Principal principal, Model model){
        Ticket ticket = ticketRepository.findById(id);
        Archive archiveTicket = new Archive(ticket.getTitle(), ticket.getTicketLvl(), ticket.getCreator(), ticket.getSummary());
        if(ticket.getCreator().getUsername().equals(principal.getName())){
            ticketRepository.deleteById(id);
        } else {
            throw new TicketDoesNotBelongToYou("There is only one thing we say to death. Not today.\n You do not own this ticket");
        }
        return new RedirectView("/main");
    }
}
