package com.example.inspire_jpa.featuers.commons.token;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    private final long ACCESS_TOKEN_EXPIRY= 1000L * 60 * 30;
    private final long REFRESH_TOKEN_EXPIRY= 1000L * 60 * 60 * 7;

    private Key getSecretKey(){
        System.out.println("debug>>>> Provider jwt secret: " + secret);
        return  Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createAT(String email) {
        System.out.println("debug>>>> Provider createAT");
        // return "Bearer XXXXXXX" ;
        return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis()+ACCESS_TOKEN_EXPIRY))
                    .signWith(getSecretKey())
                    .compact(); 
    }
    public String createRT(String email) {
        System.out.println("debug>>>> Provider createRT");
        // return "xxxxxxxxxxxxxxxx" ; 
        return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis()+REFRESH_TOKEN_EXPIRY))
                    .signWith(getSecretKey())
                    .compact(); 
    }

    //AT를 통해서 subject 추출하는 상황
    public String getUserEmailFromAT(String at){
        System.out.println("debug>>>> Provider getUserEmailFromAT at: "+ at);
        return null;
    }

}


