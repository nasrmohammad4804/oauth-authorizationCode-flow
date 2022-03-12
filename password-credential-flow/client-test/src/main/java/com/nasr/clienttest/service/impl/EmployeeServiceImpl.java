package com.nasr.clienttest.service.impl;

import com.nasr.clienttest.domain.Employee;
import com.nasr.clienttest.repository.EmployeeRepository;
import com.nasr.clienttest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public void save(Employee employee) {
        repository.save(employee);
    }

    @Override
    public Employee getByUsername(String username) throws UsernameNotFoundException {
       return repository.findEmployeeByUsername(username)
        .orElseThrow( () -> new UsernameNotFoundException("this username dont exists"));
    }
}
