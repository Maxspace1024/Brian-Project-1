package com.brian.stylish.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;

@Component
@Log4j2
public class RateLimitFilter extends OncePerRequestFilter {

    private final Long MAX_REQUESTS_COUNT = 10L;

    private final Long EXPIRE = 1L;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String key = String.format("%s_%s", request.getRemoteAddr(), request.getRequestURI());
        try {
            Long count = redisTemplate.opsForValue().increment(key);

            if (count == null || count < 1 || count > MAX_REQUESTS_COUNT) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write("{\"error\":\"too many request\"}");
                return;
            } else if (count == 1) {
                redisTemplate.expire(key, Duration.ofSeconds(EXPIRE));
            }
        } catch (Exception e) {

        }

        filterChain.doFilter(request, response);
    }
}
