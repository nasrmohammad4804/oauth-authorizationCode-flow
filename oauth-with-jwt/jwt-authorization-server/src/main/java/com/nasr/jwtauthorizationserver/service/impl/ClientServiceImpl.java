package com.nasr.jwtauthorizationserver.service.impl;

import com.nasr.jwtauthorizationserver.domain.Client;
import com.nasr.jwtauthorizationserver.repository.ClientRepository;
import com.nasr.jwtauthorizationserver.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository repository;

    @Override
    public void save(Client client) {
        repository.save(client);
    }

    @Override
    public Client getByClientId(String clientId) {
        return repository.findByClientId(clientId)
                .orElseThrow(() -> new ClientRegistrationException("this client dont exists"));
    }

    @Override
    public boolean isEmpty() {
        return repository.count()==0;
    }
}
