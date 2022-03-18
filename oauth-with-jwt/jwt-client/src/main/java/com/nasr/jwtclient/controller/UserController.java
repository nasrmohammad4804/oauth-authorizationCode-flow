package com.nasr.jwtclient.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasr.jwtclient.domain.User;
import com.nasr.jwtclient.exception.JwtNotValidException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.stream.Collectors;

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
    public RedirectView getToken(@RequestParam("code") String code) {

        String url = "http://localhost:8081/oauth/token?grant_type=authorization_code&code=" + code;
        HttpHeaders httpHeaders = new HttpHeaders();
        String urlEncoded = new String(Base64.encodeBase64((clientId + ":" + secretKey).getBytes()));

        httpHeaders.add("Authorization", "Basic " + urlEncoded);

        RestTemplate template = new RestTemplate();

        var response = template.
                exchange(url, HttpMethod.POST, new HttpEntity<>(httpHeaders), String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response.getBody());
            String token = node.path("access_token").asText();

            String resourceUrl = "http://localhost:8082/user/info";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);

            var responseEntity = template
                    .exchange(resourceUrl, HttpMethod.GET, new HttpEntity<>(headers), User.class);

            User user = responseEntity.getBody();

            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(
                            user.getUserName(), user.getPassword(),
                            Arrays.stream(user.getAuthorities()).map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList())));

            return new RedirectView("/panel");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new JwtNotValidException(e.getMessage());
        }

    }
}
