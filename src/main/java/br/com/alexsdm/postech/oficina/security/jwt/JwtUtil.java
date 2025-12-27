package br.com.alexsdm.postech.oficina.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.key}")
    private String key;
    private final long expirationMillis = 3600000;

    public String gerarToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(Keys.hmacShaKeyFor(key.getBytes()))
                .compact();
    }

    private Claims extrairClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(key.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String extrairUsername(String token) {
        return extrairClaims(token).getSubject();
    }

    public String extrairRole(String token) {
        return extrairClaims(token).get("role", String.class);
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder().
                    setSigningKey(Keys.hmacShaKeyFor(key.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }


}

