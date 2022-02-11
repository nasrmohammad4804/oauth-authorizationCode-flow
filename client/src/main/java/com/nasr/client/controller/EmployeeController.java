package com.nasr.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasr.client.domain.Employee;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Controller
public class EmployeeController {

    @GetMapping("/getEmployees")
    public String getEmployeeInfo() {

        return "getEmployees";
    }

    @GetMapping("/showEmployees")
    public ModelAndView showEmployees(@RequestParam("code") String code) throws JsonProcessingException {
        ResponseEntity<String> response;
        System.out.println("Authorization Code------" + code);

        RestTemplate restTemplate = new RestTemplate();

        // According OAuth documentation we need to send the client id and secret key in the header for authentication and get token
        String credentials = "client:secret";
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Basic " + encodedCredentials);

        HttpEntity<String> request = new HttpEntity<>(headers);

        String access_token_url = "http://localhost:8080/oauth/token";
        access_token_url += "?code=" + code;
        access_token_url += "&grant_type=authorization_code";
        access_token_url += "&redirect_uri=http://localhost:8090/showEmployees";

        response = restTemplate.exchange(access_token_url, HttpMethod.POST, request, String.class);
        System.out.println("access token response is : " + response.getBody());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response.getBody());
        String token = node.path("access_token").asText();
        String url = "http://localhost:8080/user/getEmployeesList";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("authorization", "Bearer " + token);

        ResponseEntity<Employee[]> exchange = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders), Employee[].class);

        ModelAndView modelAndView = new ModelAndView("showEmployees");
        modelAndView.addObject("employees", exchange.getBody());
        return modelAndView;

    }
}
