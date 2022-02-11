package com.nasr.security.controller;

import com.nasr.security.domain.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class EmployeeController {

    @GetMapping("/user/getEmployeesList")
    public ResponseEntity<List<Employee>> getEmployeeList(){

        return ResponseEntity.ok(
                Arrays.asList(new Employee("1","mohammad"),
                        new Employee("2","javad"),new Employee("5","ahmad"))
        );
    }

}
