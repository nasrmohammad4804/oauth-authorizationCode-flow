package com.nasr.jwtresourceserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Value("${private-key}")
    private String privateKey;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().mvcMatchers("/user/info").hasAuthority("SCOPE_read")
                .anyRequest().authenticated();

        http.oauth2ResourceServer()
                .jwt().decoder(jwtDecoder());
    }

    @Bean
    public JwtDecoder jwtDecoder(){

        SecretKey secretKey=new SecretKeySpec(privateKey.getBytes(),"HMACSHA256");

        return NimbusJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

}
