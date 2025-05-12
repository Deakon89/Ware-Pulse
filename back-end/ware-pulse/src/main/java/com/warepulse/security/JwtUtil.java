package com.warepulse.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long validityMs;

    // Genera un token per lo username
    public String generateToken(String username) {
        Date now = new Date();
        return Jwts.builder()
                   .setSubject(username)
                   .setIssuedAt(now)
                   .setExpiration(new Date(now.getTime() + validityMs))
                   .signWith(SignatureAlgorithm.HS256, secretKey)
                   .compact();
    }

    // Estrae lo username dal token
    public String extractUsername(String token) {
        return Jwts.parser()
                   .setSigningKey(secretKey)
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();
    }

    // Controlla firma e scadenza
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            // include MalformedJwtException, SignatureException, ExpiredJwtException...
            return false;
        } catch (IllegalArgumentException e) {
            // token null o vuoto
            return false;
        }
    }
}

