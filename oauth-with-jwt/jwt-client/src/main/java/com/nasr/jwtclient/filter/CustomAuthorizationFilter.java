package com.nasr.jwtclient.filter;

import com.nasr.jwtclient.domain.User;
import com.nasr.jwtclient.exception.JwtNotValidException;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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

@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private static final String TOKEN_TYPE = "Bearer ";
    private static final String[] permitEndPoint = {"/login", "/home", "/", "", "/get-token"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization")==null ? request.getParameter("Authorization") : request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_TYPE)) {
            String token = authorizationHeader.replace(TOKEN_TYPE, "");

            String url = "http://localhost:8082/user/info";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            RestTemplate template = new RestTemplate();

            try {
                var responseEntity = template.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), User.class);
                User user = responseEntity.getBody();
                SecurityContextHolder.getContext()
                        .setAuthentication(new UsernamePasswordAuthenticationToken(
                                user.getUserName(), null, Arrays.stream(user.getAuthorities()).map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
                        );
                filterChain.doFilter(request, response);

            } catch (Exception e) {
                throw new JwtNotValidException("your token not valid");
            }
        } else filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        return Arrays.stream(permitEndPoint).collect(Collectors.toList())
                .contains(request.getServletPath());
    }
}
