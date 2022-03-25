package com.nasr.jwtauthorizationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(
                User.withUsername("mohammad")
                        .password(passwordEncoder().encode("1234"))
                        .authorities("ROLE_ADMIN","write")
                        .build()
        );
        return userDetailsManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors()
                .and().csrf().disable();

        http.httpBasic();
        http.formLogin().loginPage("/login")
        .and().authorizeRequests().mvcMatchers("/login")
        .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
