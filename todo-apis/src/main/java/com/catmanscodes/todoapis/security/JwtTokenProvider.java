package com.catmanscodes.todoapis.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String secretKey;

    @Value("${app.jwt-expiration-milliseconds}")
    private long expirationTimeMilliSeconds;

    // Generate Token
    public String generateToken(Authentication authentication) {

        String username = authentication.getName();
        Date currentDate = new Date();
        Date expiryDate = new Date(System.currentTimeMillis() + expirationTimeMilliSeconds);

        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expiryDate)
                .signWith(key())
                .compact();

    }

    //Validate Token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parse(token);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

    }

    //Get User details from token
    public String getUsernameFromToken(String token) {

        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

}
