package com.brian.stylish.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Log4j2
public class MyFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // take the token
        String authorizationToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        // check token is valid authorization token
        if (authorizationToken == null || !authorizationToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // parse authz token to jwt token
            authorizationToken = authorizationToken.replace("Bearer ", "");
            String email = jwtTokenUtils.extractUsername(authorizationToken);

            SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    List.of()
                )
            );
        } catch (ExpiredJwtException | MalformedJwtException e) {

        }

        filterChain.doFilter(request, response);
    }
}
