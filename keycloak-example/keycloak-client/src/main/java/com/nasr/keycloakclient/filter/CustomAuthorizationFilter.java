package com.nasr.keycloakclient.filter;

import com.nasr.keycloakclient.domain.User;
import com.nasr.keycloakclient.exception.JwtNotValidToken;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.springframework.http.HttpMethod.GET;

@Component
@Primary
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final RestTemplate template = new RestTemplate();
    private final String[] permitEndPoints = {"/login", "/home", "", "/", "/get-token"};


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization") == null ? request.getParameter("Authorization") : request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            String token = authorizationHeader.replace("Bearer ", "");
            String url = "http://localhost:9090/user/info";
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + token);

            try {
                ResponseEntity<User> responseEntity = template.exchange(url, GET, new HttpEntity<>(httpHeaders), User.class);
                var user = responseEntity.getBody();

                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(user.getUserName(), null, user.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
                );
                filterChain.doFilter(request, response);

            } catch (Exception e) {
                throw new JwtNotValidToken(e.getMessage());
            }


        } else filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Arrays.stream(permitEndPoints).collect(Collectors.toList()).contains(request.getServletPath());
    }
}
