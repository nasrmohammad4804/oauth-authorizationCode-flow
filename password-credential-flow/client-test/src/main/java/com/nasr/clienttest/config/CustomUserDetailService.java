package com.nasr.clienttest.config;

import com.nasr.clienttest.domain.Employee;
import com.nasr.clienttest.service.EmployeeService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Primary
public class CustomUserDetailService implements UserDetailsService {


    private final EmployeeService employeeService;

    public CustomUserDetailService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee= employeeService.getByUsername(username);

        return new CustomUserDetail(employee);
    }
}
