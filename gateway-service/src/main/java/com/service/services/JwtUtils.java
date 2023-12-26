package com.service.services;

import java.security.Key;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;;

@Service
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    @PostConstruct
    public void initKey() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public boolean isExpired(String token) {
        try {
            return getClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isValidToken(String token) {
    	 try {
             Claims claims = getClaims(token);
             Date expiration = claims.getExpiration();
             return expiration != null && !expiration.before(new Date());
         } catch (Exception e) {
             return false;
         }
    }
}