package com.teamLong.java401d.midterm.troublemaker.controller;

import com.teamLong.java401d.midterm.troublemaker.model.Severity;
import com.teamLong.java401d.midterm.troublemaker.model.Ticket;
import com.teamLong.java401d.midterm.troublemaker.model.UserAccount;
import com.teamLong.java401d.midterm.troublemaker.repository.TicketRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketRepository ticketRepository;



    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "redirect:/login";
    }

    // open test route for main
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(Principal user, Model model) {
        UserAccount currentUser = (UserAccount)((UsernamePasswordAuthenticationToken) user).getPrincipal();
        model.addAttribute("principal",currentUser);
        List<Ticket> tickets = ticketRepository.findAllByCreatorId(currentUser.getId());
        model.addAttribute("tickets", tickets);
        List<Enum> enumValues = new ArrayList<Enum>(EnumSet.allOf(Severity.class));
        model.addAttribute("enumValues", enumValues);
        return "main";
    }
}