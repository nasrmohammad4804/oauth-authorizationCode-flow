package com.nasr.clienttest.service;

import com.nasr.clienttest.domain.Employee;

public interface EmployeeService {

    void save(Employee employee);

    Employee getByUsername(String username);
}
