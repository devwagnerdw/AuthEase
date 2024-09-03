package com.client.ws.ws.Service.impl;

import com.client.ws.ws.Service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${webservices.rasplus.jwt.expiration}")
    private String expiration;

    @Value("${webservices.rasplus.jwt.secret}")
    private String secret;

    @Override
    public String getToken(Long userId) {

        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
        return Jwts.builder()
                .setIssuer("API Rasmoo Plus")
                .setSubject(userId.toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    @Override
    public Boolean isValid(String token) {
        try {
            getClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Long getUserId(String token) {
        Jws<Claims> claims = getClaimsJws(token);
        return Long.parseLong(claims.getBody().getSubject());
    }

    private Jws<Claims> getClaimsJws(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
    }
}
