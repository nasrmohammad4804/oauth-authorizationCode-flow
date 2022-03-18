package com.nasr.jwtresourceserver.controller;

import com.nasr.jwtresourceserver.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @GetMapping("/user/info")
    public User sayHello(OAuth2Authentication authentication) {

        return new User(
                authentication.getUserAuthentication().getName(),
                null,authentication.getUserAuthentication().getAuthorities()
        .stream().map(GrantedAuthority::getAuthority).toArray(String[]::new));
    }
}
