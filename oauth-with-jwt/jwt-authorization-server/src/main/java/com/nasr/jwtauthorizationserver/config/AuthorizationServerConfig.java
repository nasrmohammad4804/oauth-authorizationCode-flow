package com.nasr.jwtauthorizationserver.config;

import com.nasr.jwtauthorizationserver.service.ClientService;
import com.nasr.jwtauthorizationserver.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {


    @Autowired
    private ClientService clientService;

    @Bean
    public ClientDetailsService myClientDetailsService() {
        return new CustomClientDetailService(clientService);
    }

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.withClientDetails(myClientDetailsService());
        //todo clientDetailService dont work then store client in memory

        clients.inMemory()
                .withClient("client1")
                .secret("secret1")
                .scopes("read")
                .authorizedGrantTypes("password")
                .and()
                .withClient("client2")
                .secret("secret2")
                .authorizedGrantTypes("authorization_code")
                .redirectUris("http://localhost:8080/get-token")
                .scopes("read");
    }

    public TokenStore tokenStore() {
        return new JwtTokenStore(new JwtAccessTokenConverter());
    }

    public JwtAccessTokenConverter converter() {
        var converter = new JwtAccessTokenConverter();
        converter.setSigningKey("secret");
        //we need to set at least signing key

        return converter;
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .accessTokenConverter(converter());
    }
}
