package com.kavya.societymaintenance.utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kavya.societymaintenance.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class AuthUtils {
    
    @Value("${jwt.secret}")
    private String JWT_SECRET_KEY;

    public SecretKey genrateSecretKey(){
        //generate the secret key used for creating jwt...
        return Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // generating jwt token from the key..
    public String generateJWTtoken(User user){
        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000))
                .signWith(genrateSecretKey())
                .compact();
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(genrateSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) &&
                !isTokenExpired(token);
    }

    // check whether token issued at a particular date is still valid...
    private boolean isTokenExpired(String token) {

        Date expiration = extractClaim(token, Claims::getExpiration);

        return expiration.before(new Date());
    }


}
