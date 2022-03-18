package com.nasr.jwtclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {

    @Value("${security.oauth.accounts}")
    private String authorizationServerAddress;

    @GetMapping("/home")
    public String homePage(){
        return "home";
    }

    @GetMapping("/panel")
    public String panelPage(Model model, Authentication authentication){
        model.addAttribute("username",authentication.getName());
        return "panel";
    }
    @GetMapping(value = {"","/"})
    public RedirectView redirectView(){
        return new RedirectView(authorizationServerAddress);
    }

}
