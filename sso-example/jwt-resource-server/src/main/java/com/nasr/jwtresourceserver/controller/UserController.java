package com.nasr.jwtresourceserver.controller;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class UserController {

    /**
     * this method for api of resource server which is in client with name of user info uri for getting this resource
     * @param principal
     * @return claim
     */
    @GetMapping("/user/info")
    public Map<String, Object> sayHello(Principal principal) {
      return   ((Jwt)((JwtAuthenticationToken)principal).getPrincipal()).getClaims();
    }
}
