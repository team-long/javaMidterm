package com.teamLong.java401d.midterm.troublemaker;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AppUserController {
    @GetMapping("/")
    public String getRootPage(){
        return "layout";
    }
    @GetMapping("/test/login")
    public String getLogin(){
        return "login";
    }
    @GetMapping("/test/signup")
    public String getSignup(){
        return "signup";
    }

    @GetMapping("/test/main")
    public String getMain(){
        return "main";
    }

}

