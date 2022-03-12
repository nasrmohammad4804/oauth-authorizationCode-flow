package com.nasr.controller;

import com.nasr.domain.Employee;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class EmployeeController {

    @GetMapping(value = "/user/getEmployeesList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> getEmployeeList() {

        return ResponseEntity.ok(
                Arrays.asList(new Employee("1", "mohammad"),
                        new Employee("2", "javad"), new Employee("5", "ahmad"))
        );
    }

    @GetMapping("/test")
    public ResponseEntity<List<Employee>> getData() {
        return ResponseEntity.ok(
                Arrays.asList(new Employee("1", "mohammad"),
                        new Employee("2", "javad"), new Employee("5", "ahmad"))
        );
    }

}
