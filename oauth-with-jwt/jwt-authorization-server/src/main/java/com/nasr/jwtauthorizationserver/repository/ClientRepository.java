package com.nasr.jwtauthorizationserver.repository;

import com.nasr.jwtauthorizationserver.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,String> {

    Optional<Client> findByClientId(String clientId);
}
