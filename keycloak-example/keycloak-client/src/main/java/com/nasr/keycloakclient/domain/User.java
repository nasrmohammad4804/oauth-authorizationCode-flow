package com.nasr.keycloakclient.domain;
import java.util.List;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private List<String> authorities;
}

