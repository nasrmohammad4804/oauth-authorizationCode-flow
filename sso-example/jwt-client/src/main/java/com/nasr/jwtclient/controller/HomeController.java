package com.nasr.jwtclient.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String homePage(){
        return "home";
    }

    @GetMapping("/panel")
    public String panelPage(Model model, Authentication authentication){
        System.out.println(authentication instanceof OAuth2AuthenticationToken);
        model.addAttribute("username",authentication.getName());
        return "panel";
    }

}
