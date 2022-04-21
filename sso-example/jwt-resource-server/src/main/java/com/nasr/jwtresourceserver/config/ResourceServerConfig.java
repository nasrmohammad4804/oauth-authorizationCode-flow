package com.nasr.jwtresourceserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    private static final String SCOPE_PREFIX = "SCOPE_";
    private static final String ALGORITHM = "HMACSHA256";

    @Value("${private-key}")
    private String privateKey;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().mvcMatchers("/user/info").hasAnyAuthority("SCOPE_read", "ROLE_ADMIN")
                .anyRequest().authenticated();

        http.oauth2ResourceServer()
                .jwt().decoder(jwtDecoder())
                .jwtAuthenticationConverter(converter());
    }

    /*by default authorization read from scope token payload and add scope prefix to each scope
     * for example have read scope then we need to use hasAuthority('SCOPE_read') in resource server but
     * we using converter we change default behavior  in BearerTokenAuthenticationFilter and get scope with authorities
     * and save to grantedAuthorities of JwtAuthenticationToken -> then if have read scope of ROLE_ADMIN then we access to resource with valid token
     * */
    private Converter<Jwt, JwtAuthenticationToken> converter() {

        return jwt -> {
            List<String> scopes = jwt.getClaimAsStringList("scope").stream().map(scope -> SCOPE_PREFIX + scope)
                    .collect(Collectors.toList());
            scopes.addAll(jwt.getClaimAsStringList("authorities"));
            Stream<SimpleGrantedAuthority> grantedAuthorities = scopes.stream().map(SimpleGrantedAuthority::new);
            return new JwtAuthenticationToken(jwt, grantedAuthorities.collect(Collectors.toList()));
        };
    }

    @Bean
    public JwtDecoder jwtDecoder() {

        SecretKey secretKey = new SecretKeySpec(privateKey.getBytes(), ALGORITHM);

        return NimbusJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

}
