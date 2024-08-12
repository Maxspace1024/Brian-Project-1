package com.brian.stylish.config;

import com.brian.stylish.dto.AccessDTO;
import com.brian.stylish.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public AccessDTO createAccessDTO(UserDTO dto) {
        String token = generateToken(dto);
        return AccessDTO.builder()
            .accessToken(token)
            .accessExpired(3600)
            .user(dto)
            .build();
    }

    public UserDTO parseTokenToUserDTO(String token) {
        Claims claims = extractAllClaims(token);
        return UserDTO.builder()
            .id(claims.get("id", Integer.class))
            .name(claims.get("name", String.class))
            .email(claims.get("email", String.class))
            .provider(claims.get("provider", String.class))
            .picture(claims.get("picture", String.class))
            .build();
    }

    public UserDTO parseTokenToUserProfile(String token) {
        UserDTO dto = parseTokenToUserDTO(token);
        dto.setId(null);
        return dto;
    }

    public String generateToken(UserDTO dto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", dto.getId());
        claims.put("name", dto.getName());
        claims.put("email", dto.getEmail());
        claims.put("provider", dto.getProvider());
        claims.put("picture", dto.getPicture());
        return createToken(claims, dto.getEmail());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
            .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
            .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
