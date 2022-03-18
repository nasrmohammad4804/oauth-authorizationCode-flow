package com.nasr.jwtauthorizationserver.service;

import com.nasr.jwtauthorizationserver.domain.Client;

public interface    ClientService {

    void save(Client client);

    Client getByClientId(String clientId);

    boolean isEmpty();
}
