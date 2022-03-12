package com.nasr.securitytest.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {


    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String password;

    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Employee(String username, String firstName, String lastName, String password) {
        this(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
