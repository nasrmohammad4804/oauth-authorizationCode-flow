package com.nasr.keycloakresourceserver.controller;

import com.nasr.keycloakresourceserver.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @GetMapping("/user/info")
    public User hello(Authentication authentication) {
        var token = ((Jwt) authentication.getCredentials());
        return User.builder().userName(token.getClaims().get("user_name").toString())
                .authorities( (List<String>) token.getClaims().get("authorities")).build();
    }


}
