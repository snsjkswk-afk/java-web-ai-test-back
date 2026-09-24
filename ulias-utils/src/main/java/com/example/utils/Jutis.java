package com.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Jutis {

    public static String GnerateJwt(Integer id, String username) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("username", username);
        String jwt = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, "jwt-secret")
                .setExpiration(new Date(System.currentTimeMillis() + 1000*6000))
                .compact();

        return jwt;
    }

    public static void ParseJwt(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey("jwt-secret")
                .parseClaimsJws(jwt)
                .getBody();

    }
    public static Claims ParseJwtt(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey("jwt-secret")
                .parseClaimsJws(jwt)
                .getBody();

        return claims;
    }
    public static Integer getUserIdFromToken(String token) {
        try {
            Claims claims = ParseJwtt(token);
            return claims.get("id", Integer.class);
        } catch (Exception e) {
            return null;
        }
    }
}
