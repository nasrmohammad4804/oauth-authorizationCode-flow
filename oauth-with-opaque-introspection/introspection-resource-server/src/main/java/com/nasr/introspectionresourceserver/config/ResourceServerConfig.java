package com.nasr.introspectionresourceserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    //when we use introspection opaque token and use resource server dependency we need to another dependency called oauth2-oidc-sdk
    @Override
    public void configure(HttpSecurity http) throws Exception {


        http.oauth2ResourceServer(
                c -> c.opaqueToken(
                        opaque -> {
                            opaque.introspectionUri("http://localhost:8080/oauth/check_token");
                            opaque.introspectionClientCredentials("resource-server", "resource-server");
                        }
                )
        );

        http.authorizeRequests()
                .anyRequest().authenticated();

        http.formLogin();

    }
}
