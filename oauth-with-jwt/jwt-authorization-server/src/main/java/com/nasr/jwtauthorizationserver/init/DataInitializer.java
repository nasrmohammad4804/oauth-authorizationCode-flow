package com.nasr.jwtauthorizationserver.init;

import com.nasr.jwtauthorizationserver.domain.Client;
import com.nasr.jwtauthorizationserver.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class DataInitializer {

    @Autowired
    private ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void createDefaultClient(){

        if(clientService.isEmpty()) {

            Client client1=Client.builder().clientId("client1").secret(passwordEncoder.encode("secret1"))
                    .grantType("password").authorities(Set.of("read"))
                    .scope("read")
                    .build();

            clientService.save(client1);
        }
    }
}
