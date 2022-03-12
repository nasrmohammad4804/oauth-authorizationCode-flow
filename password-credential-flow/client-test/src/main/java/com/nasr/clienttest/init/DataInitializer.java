package com.nasr.clienttest.init;

import com.nasr.clienttest.domain.Employee;
import com.nasr.clienttest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataInitializer {

    @Autowired
    private EmployeeService employeeService;

    @PostConstruct
    public void createCustomEmployee(){

        Employee employee=new Employee("mohammad","1234");
        employeeService.save(employee);

    }
}
