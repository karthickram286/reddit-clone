package com.karthickram.redditclone.service;

import com.karthickram.redditclone.models.User;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class JWTProvider {

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUserName())
                .signWith(getPrivateKey())
                .compact();
    }
}
