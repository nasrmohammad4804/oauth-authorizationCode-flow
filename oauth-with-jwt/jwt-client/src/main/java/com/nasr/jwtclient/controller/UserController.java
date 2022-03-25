package com.nasr.jwtclient.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Value("${security.client-id}")
    public String clientId;

    @Value("${security.secret-key}")
    public String secretKey;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/get-token")
    public String getToken(@RequestParam("code") String code, RedirectAttributes attributes) {

        String url = "http://localhost:8081/oauth/token?grant_type=authorization_code&code=" + code;
        HttpHeaders httpHeaders = new HttpHeaders();
        String urlEncoded = new String(Base64.encodeBase64((clientId + ":" + secretKey).getBytes()));

        httpHeaders.add("Authorization", "Basic " + urlEncoded);

        RestTemplate template = new RestTemplate();

        var exchange = template.
                exchange(url, HttpMethod.POST, new HttpEntity<>(httpHeaders), String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(exchange.getBody());
            String token = node.path("access_token").asText();
            System.out.println(token);

            attributes.addAttribute("Authorization", "Bearer " + token);
            return "redirect:/panel";

        } catch (Exception e) {
           throw new RuntimeException(e.getMessage());
        }
    }
}
