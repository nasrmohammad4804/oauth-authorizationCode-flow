package com.nasr.jwtauthorizationserver.config;

import com.nasr.jwtauthorizationserver.domain.Client;
import com.nasr.jwtauthorizationserver.service.ClientService;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;



public class CustomClientDetailService implements ClientDetailsService {


    private  ClientService clientService;

    public CustomClientDetailService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        Client client = clientService.getByClientId(clientId);
/*
        Date tokenExpiration = new Date(client.getTokenValiditySeconds());

        if (new Date().after(tokenExpiration))
            throw new ClientRegistrationException("token expiration not valid");*/

        return  new CustomClientDetails(client);

    }
}
