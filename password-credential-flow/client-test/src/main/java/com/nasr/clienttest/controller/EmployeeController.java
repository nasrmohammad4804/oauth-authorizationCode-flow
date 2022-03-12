package com.nasr.clienttest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasr.clienttest.domain.Employee;
import com.nasr.clienttest.service.EmployeeService;
import lombok.Getter;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.nasr.clienttest.Constant.CLIENT_ID;
import static com.nasr.clienttest.Constant.SECRET_KEY;

@RestController
public class EmployeeController {

    private final RestTemplate template=new RestTemplate();

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAll() throws JsonProcessingException {

        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();

        Employee employee = employeeService.getByUsername(authentication.getName());

        System.out.println("username "+employee.getUsername()+" password "+employee.getPassword());
        String url="http://localhost:8081/oauth/token?grant_type=password&username="+employee.getUsername()
                +"&password="+employee.getPassword();


        String encoding=new String(Base64.encodeBase64((CLIENT_ID+":"+SECRET_KEY).getBytes()));
        HttpHeaders headers=new HttpHeaders();
        headers.add("Authorization","Basic "+encoding);
        ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.POST, new HttpEntity<>(headers), String.class);


        System.out.println("token is recived is : "+responseEntity.getBody());
        HttpHeaders httpHeaders=new HttpHeaders();

        ObjectMapper mapper=new ObjectMapper();

        JsonNode node = mapper.readTree(responseEntity.getBody());
        String token = node.path("access_token").asText();

        System.out.println(node.asText());
        httpHeaders.add("Authorization","Bearer "+token);
        ResponseEntity<Employee[]> response = template.
                exchange("http://localhost:8081/all-employee", HttpMethod.GET, new HttpEntity<>(httpHeaders),Employee[].class);


        return ResponseEntity.ok(
                Arrays.stream(Objects.requireNonNull(response.getBody())).collect(Collectors.toList())
        );
    }
}
