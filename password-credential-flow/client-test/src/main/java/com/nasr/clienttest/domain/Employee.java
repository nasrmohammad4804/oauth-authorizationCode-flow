package com.nasr.clienttest.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
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
