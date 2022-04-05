package com.nasr.jwtauthorizationserver.config;

import com.nasr.jwtauthorizationserver.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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

    @Value("${private-key}")
    private String privateKey;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.withClientDetails(myClientDetailsService());

        clients.inMemory()
                .withClient("client1")
                .secret("secret1")
                .scopes("read")
                .authorizedGrantTypes("password")
                .and()
                .withClient("client2")
                .secret("secret2")
                .autoApprove(true)
                .authorizedGrantTypes("authorization_code")
                .redirectUris("http://localhost:8080/login/oauth2/code/app")
                .resourceIds("app")
                .scopes("read");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()")
        .tokenKeyAccess("permitAll()");
    }

    public TokenStore tokenStore() {
        return new JwtTokenStore(new JwtAccessTokenConverter());
    }

    //using symmetric key for jwt token
    public JwtAccessTokenConverter converter() {
        var converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        return converter;
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .accessTokenConverter(converter());
    }
}
