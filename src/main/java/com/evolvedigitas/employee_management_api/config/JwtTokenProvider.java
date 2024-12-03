package com.evolvedigitas.employee_management_api.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Component
public class JwtTokenProvider {

//    private final String JWT_SECRET = "your_secret_key";
    private final long JWT_EXPIRATION = 86400000L; //1 day

    private final SecretKey key;

    public JwtTokenProvider(){
        try{
            KeyGenerator keyGen= KeyGenerator.getInstance("HmacSHA256");
            this.key= keyGen.generateKey();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) key).build().parseUnsecuredClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().verifyWith((SecretKey) key).build().parseUnsecuredClaims(token).getPayload().getSubject();
    }
}
