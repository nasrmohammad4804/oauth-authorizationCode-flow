package com.nasr.keycloakclient.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.auth.JWTAuthentication;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SignInController {

    @Value("${client-id}")
    private String clientId;

    @Value("${client-secret}")
    private String clientSecret;

    private final RestTemplate template = new RestTemplate();

    @GetMapping("/get-token")
    public String login(@RequestParam("code") String code, RedirectAttributes attributes) {

        String url = "http://localhost:8080/realms/app/protocol/openid-connect/token";
        try {

            Map<Object, List<Object>> map = new HashMap<>();
            map.put("code", List.of(code));
            map.put("grant_type", List.of("authorization_code"));
            map.put("client_id", List.of(clientId));
            map.put("client_secret", List.of(clientSecret));

            var multiValueMap = new LinkedMultiValueMap<>(map);


            HttpEntity<Object> httpEntity = new HttpEntity<>(multiValueMap);

            ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.POST, httpEntity, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(responseEntity.getBody());
            String token = node.path("access_token").asText();
            attributes.addAttribute("Authorization", "Bearer " + token);
            return "redirect:/panel";
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
