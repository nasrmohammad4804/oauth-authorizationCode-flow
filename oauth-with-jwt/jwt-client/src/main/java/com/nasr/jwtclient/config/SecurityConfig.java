package com.nasr.jwtclient.config;

import com.nasr.jwtclient.filter.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthorizationFilter filter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login");
        http.logout();

        http.authorizeRequests()
                .mvcMatchers("/home", "/login", "/get-token", "", "/").permitAll()
                .anyRequest().authenticated();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);

    }
}
