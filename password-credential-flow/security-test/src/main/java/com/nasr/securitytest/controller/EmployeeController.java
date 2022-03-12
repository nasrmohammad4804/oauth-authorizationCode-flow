package com.nasr.securitytest.controller;

import com.nasr.securitytest.domain.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
public class EmployeeController {

    @GetMapping("/all-employee")
    public ResponseEntity<List<Employee>> getAll(){

        return ResponseEntity.ok( List.of(
                new Employee("mohammad","nasr"),
                new Employee("javad","zare"),
                new Employee("taha","karimi")
        ));
    }
}
