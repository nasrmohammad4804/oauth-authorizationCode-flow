package com.nasr.keycloakclient.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
public class ClientSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Filter filter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/home","/get-token","","/").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().and().cors()
                .disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
