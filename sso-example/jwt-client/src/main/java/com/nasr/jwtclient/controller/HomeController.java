package com.nasr.jwtclient.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String homePage(){
        return "home";
    }

    @GetMapping("/panel")
    public String panelPage(Model model, Authentication authentication){
        model.addAttribute("username",authentication.getName());
        return "panel";
    }

}
