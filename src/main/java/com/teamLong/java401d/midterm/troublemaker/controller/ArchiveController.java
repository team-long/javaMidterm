package com.teamLong.java401d.midterm.troublemaker.controller;

import com.teamLong.java401d.midterm.troublemaker.model.Archive;
import com.teamLong.java401d.midterm.troublemaker.model.Severity;
import com.teamLong.java401d.midterm.troublemaker.model.Ticket;
import com.teamLong.java401d.midterm.troublemaker.model.UserAccount;
import com.teamLong.java401d.midterm.troublemaker.repository.ArchiveRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.TicketRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.security.Principal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Controller
public class ArchiveController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    ArchiveRepository archiveRepository;


    @PostMapping("delete/ticket/{id}")
    public RedirectView deleteTicket(@PathVariable long id, Principal principal, Model model){
        Ticket ticket = ticketRepository.findById(id);
        if(ticket.isArchived() == true){
            Archive archiveTicket = new Archive();
            archiveTicket.setTitle(ticket.getTitle());
            archiveTicket.setTicketLvl(ticket.getTicketLvl());
            archiveTicket.setCreator(ticket.getCreator());
            archiveTicket.setSummary(ticket.getSummary());
            archiveTicket.setCreatedAt(ticket.getCreatedAt());
            archiveRepository.save(archiveTicket);
            if(ticket.getCreator().getUsername().equals(principal.getName())){
                ticketRepository.deleteById(id);
            } else {
                throw new TicketDoesNotBelongToYou("There is only one thing we say to death. Not today.\n You do not own this ticket");
            }

        } else {
            throw new TicketDoesNotBelongToYou("There is only one thing we say to death. Not today.\n You do not own this ticket");
        }
        return new RedirectView("/main");
    }

    @RequestMapping(value = "/archive", method = RequestMethod.GET)
    public String main(Principal user, Model model) {
        UserAccount currentUser = (UserAccount)((UsernamePasswordAuthenticationToken) user).getPrincipal();
        model.addAttribute("principal",currentUser);
        if(userRepository.findByUsername(user.getName()).isAdmin()){
            Iterable<Archive> archive = archiveRepository.findAll();
            model.addAttribute("tickets", archive);
            List<Enum> enumValues = new ArrayList<Enum>(EnumSet.allOf(Severity.class));
            model.addAttribute("enumValues", enumValues);
        } else {
            Iterable<Archive> archive = archiveRepository.findAllByCreatorId(currentUser.getId());
            model.addAttribute("tickets", archive);
            List<Enum> enumValues = new ArrayList<Enum>(EnumSet.allOf(Severity.class));
            model.addAttribute("enumValues", enumValues);
        }

        return "archive";
    }
}
