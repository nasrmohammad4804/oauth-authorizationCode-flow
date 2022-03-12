package com.nasr.securitytest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@EnableResourceServer
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter  {
    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.requestMatchers().mvcMatchers("/all-employee")
        .and().authorizeRequests().anyRequest()
        .access("#oauth2.hasScope('read')");
    }
}
