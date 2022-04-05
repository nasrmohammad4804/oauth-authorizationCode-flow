package com.nasr.jwtclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.logout();

        http.authorizeRequests()
                .mvcMatchers("/home").permitAll()
                .anyRequest().authenticated().and().oauth2Login()
                .defaultSuccessUrl("/panel",true)
                .clientRegistrationRepository(repository());

    }

    @Bean
    public ClientRegistrationRepository repository() {
        return new InMemoryClientRegistrationRepository(registrations());
    }

    @Bean
    public List<ClientRegistration> registrations() {

        return List.of(
                ClientRegistration.withRegistrationId("app")
                        .clientId("client2")
                        .clientSecret("secret2").authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationUri("http://localhost:8081/oauth/authorize")
                        .tokenUri("http://localhost:8081/oauth/token")
                        .redirectUri("http://localhost:8080/login/oauth2/code/app")
                        .userInfoUri("http://localhost:8082/user/info")
                        .userNameAttributeName("user_name")
                        .scope("read")
                        .build()
        );
    }
}
