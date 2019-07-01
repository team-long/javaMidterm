package com.teamLong.java401d.midterm.troublemaker.controller;


import com.teamLong.java401d.midterm.troublemaker.model.Severity;
import com.teamLong.java401d.midterm.troublemaker.model.Ticket;
import com.teamLong.java401d.midterm.troublemaker.model.UserAccount;
import com.teamLong.java401d.midterm.troublemaker.email.EmailSender;
import com.teamLong.java401d.midterm.troublemaker.model.*;
import com.teamLong.java401d.midterm.troublemaker.repository.RoleRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.TicketRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.UpdateRepository;
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
import java.util.Set;

@Controller
public class TicketController {

    @Autowired
    UpdateRepository updateRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    @GetMapping("/ticket")
    public String createTicketPage(Model model, Principal user){
        List<Enum> enumValues = new ArrayList<Enum>(EnumSet.allOf(Severity.class));
        model.addAttribute("principal", userRepository.findByUsername(user.getName()));
        model.addAttribute("enumValues", enumValues);
        return "ticket";
    }

    @GetMapping("/ticket/{ticketId}")
    public String getOneTicketPage(@PathVariable long ticketId, Model m, Principal user ){
        Ticket oneTicket = ticketRepository.findById(ticketId);
        UserAccount getUser = userRepository.findByUsername(user.getName());
        List<Update> comments = updateRepository.findAllByTicketId(ticketId);
        m.addAttribute("comments", comments);
        m.addAttribute("ticket", oneTicket);
        m.addAttribute("principal", getUser);
        return "ticket-detail";
    }

    @PostMapping("/create/ticket")
    public RedirectView makeATicket(String title, String ticketLvl, String summary, Principal p, Model model){
        UserAccount user = userRepository.findByUsername(p.getName());
        Ticket ticket = new Ticket(title, Severity.valueOf(ticketLvl), user, summary);
        ticketRepository.save(ticket);
        model.addAttribute("ticket", ticket);
        List<String> emails = new ArrayList<>();
        //seems convoluted just to get email list - better way via the model?
        roleRepository.findByRole("admin").getUserAccounts().forEach(userAccount -> emails.add(userAccount.getUsername()));
        EmailSender.sendEmail(user, emails, "CREATE", ticket);
        return new RedirectView("/main");
    }


    //all tickets user route
    @GetMapping("/tickets/all")
    public String getAllTickets(Principal principal, Model model){
        //Did we need to pass on full user model? duplication of efforts here.
        UserAccount loggedInUser = userRepository.findByUsername(principal.getName());
        model.addAttribute("loggedInUser", loggedInUser);
        Iterable<Ticket> tickets = ticketRepository.findAll();
        model.addAttribute("tickets", tickets);
        model.addAttribute("principal", userRepository.findByUsername(principal.getName()));
        return "allTickets";
    }

    @GetMapping("/edit/{id}")
    public String editTicket(@PathVariable long id, Principal principal, Model model){
        Ticket ticket = ticketRepository.findById(id);
        model.addAttribute("ticket", ticket);
        UserAccount loggedInUser = userRepository.findByUsername(principal.getName());
        model.addAttribute("principal", loggedInUser);
        List<Enum> enumValues = new ArrayList<Enum>(EnumSet.allOf(Severity.class));
        model.addAttribute("enumValues", enumValues);
        return "edit";
    }
    //put all of the incoming individual parameters in a model?
    @PostMapping("/tickets/edit/{id}")
    public RedirectView updateTicket(@PathVariable long id, String title, String ticketLvl, String summary, Principal principal, Model model){
        Ticket ticket = ticketRepository.findById(id);
        if(ticket.getCreator().getUsername().equals(principal.getName()) || userRepository.findByUsername(principal.getName()).isAdmin()){
            ticket.setTitle(title);
            ticket.setTicketLvl(Severity.valueOf(ticketLvl));
            ticket.setSummary(summary);
            ticketRepository.save(ticket);
        } else {
            throw new TicketDoesNotBelongToYou("There is only one thing we say to death. Not today.\n You do not own this ticket");
        }
        return new RedirectView("/main");
    }

    @PostMapping("tickets/comment")
    public String addComment(long ticketId, String update, Principal user) {
        Ticket ticket = ticketRepository.findById(ticketId);
        Update createComment = new Update(update, ticket, user.getName());
        updateRepository.save(createComment);
        return "redirect:/ticket/"+ticketId;
    }

    @PostMapping("tickets/resolve")
    public String resolveTicket(long ticketId, Principal principal) {
        Ticket ticket = ticketRepository.findById(ticketId);

        if(userRepository.findByUsername(principal.getName()).isAdmin()) {
            ticket.setArchived(true);
            ticketRepository.save(ticket);
            EmailSender.sendEmail(ticket.getCreator(), null, "RESOLVED", ticket);
        }
        return "redirect:/ticket/"+ticketId;
    }
}
//Do we really need this? safety check might not be necessary.
@ResponseStatus(value = HttpStatus.FORBIDDEN)
class TicketDoesNotBelongToYou extends RuntimeException {
    public TicketDoesNotBelongToYou(String string){
        super(string);
    }
}
