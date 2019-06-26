package com.teamLong.java401d.midterm.troublemaker.controller;

import com.teamLong.java401d.midterm.troublemaker.email.EmailSender;
import com.teamLong.java401d.midterm.troublemaker.model.Severity;
import com.teamLong.java401d.midterm.troublemaker.model.Ticket;
import com.teamLong.java401d.midterm.troublemaker.model.UserAccount;
import com.teamLong.java401d.midterm.troublemaker.repository.RoleRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.TicketRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Controller
public class TicketController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    @GetMapping("/ticket")
    public String createTicketPage(Model model){
        List<Enum> enumValues = new ArrayList<Enum>(EnumSet.allOf(Severity.class));
        model.addAttribute("enumValues", enumValues);
        return "ticket";
    }

    @GetMapping("/ticket/{ticketId}")
    public String getOneTicketPage(@PathVariable long ticketId, Model m ){
        Ticket oneTicket = ticketRepository.findById(ticketId);
        m.addAttribute("ticket", oneTicket);
        return "ticket-detail";
    }

    @PostMapping("/create/ticket")
    public RedirectView makeATicket(String title, String ticketLvl, String summary, Principal p, Model model){
        UserAccount user = userRepository.findByUsername(p.getName());
        Ticket ticket = new Ticket(title, Severity.valueOf(ticketLvl), user, summary);
        ticketRepository.save(ticket);
        model.addAttribute("ticket", ticket);
        List<String> emails = new ArrayList<>();
        roleRepository.findByRole("admin").getUserAccounts().forEach(userAccount -> emails.add(userAccount.getUsername()));
        EmailSender.sendEmail(user, emails, "CREATE", ticket);
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
        List<Enum> enumValues = new ArrayList<Enum>(EnumSet.allOf(Severity.class));
        model.addAttribute("enumValues", enumValues);
        return "edit";
    }

    @PostMapping("/tickets/edit/{id}")
    public RedirectView updateTicket(@PathVariable long id, String title, String ticketLvl, String summary, Principal principal, Model model){
        Ticket ticket = ticketRepository.findById(id);
        if(ticket.getCreator().getUsername().equals(principal.getName())){
//            ticket.setArchived();
            ticket.setTitle(title);
            ticket.setTicketLvl(Severity.valueOf(ticketLvl));
            ticket.setSummary(summary);
            ticketRepository.save(ticket);
        } else {
            throw new TicketDoesNotBelongToYou("There is only one thing we say to death. Not today.\n You do not own this ticket");
        }
        return new RedirectView("/main");
    }


//    @DeleteMapping("delete/ticket/{id}")
//    public RedirectView deleteTicket(@PathVariable long id, Principal principal, Model model){
//        Ticket ticket = ticketRepository.findById(id);
//        if(ticket.getCreator().getUsername().equals(principal.getName())){
//            ticketRepository.deleteById(id);
//        } else {
//            throw new TicketDoesNotBelongToYou("There is only one thing we say to death. Not today.\n You do not own this ticket");
//        }
//        return new RedirectView("/main");
//    }

}

@ResponseStatus(value = HttpStatus.FORBIDDEN)
class TicketDoesNotBelongToYou extends RuntimeException {
    public TicketDoesNotBelongToYou(String string){
        super(string);
    }
}
