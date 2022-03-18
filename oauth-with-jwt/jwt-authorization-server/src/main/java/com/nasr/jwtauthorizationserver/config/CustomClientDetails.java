package com.nasr.jwtauthorizationserver.config;

import com.nasr.jwtauthorizationserver.domain.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomClientDetails implements ClientDetails {

    private final Client client;

    @Override
    public String getClientId() {
        return client.getClientId();
    }

    @Override
    public Set<String> getResourceIds() {
        return null;
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return client.getSecret();
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        return Set.of(client.getScope());
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return Set.of(client.getGrantType());
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return Set.of(client.getRedirectUri());
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return client.getAuthorities()
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
//        return (int)client.getTokenValiditySeconds();

        return null;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return null;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
