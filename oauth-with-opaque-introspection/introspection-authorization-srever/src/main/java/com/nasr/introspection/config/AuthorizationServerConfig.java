package com.nasr.introspection.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(NoOpPasswordEncoder.getInstance())
                .checkTokenAccess("isAuthenticated()");


        /*
          i tell to spring security authenticated /oauth/check_token?token=..... isAuthenticated()
          because in introspection flow which authorization server & resource server on different application resource server
          then we need to this endpoint on resource server for find out opaque token valid or not
          if resource server & authorization server on same application dont need to this api because both access to tokenStore
          because tokenStore access on application and both of them use that
         */

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client1")
                .secret("secret1")
                .authorizedGrantTypes("password")
                .accessTokenValiditySeconds(5 * 60)
                .scopes("read")
                .and()
                .withClient("resource-server")
                .secret("resource-server"); //because we use isAuthenticated() for checking token then resource server for send this request must be authenticated
        //then resource server same as client and that must send client id and secret key then we need new client in this method
        //and then we dont need authorizedGrantTypes because dont need to obtain token goal of add this client for authentication of resource server


        //using of opaque token
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }
}
