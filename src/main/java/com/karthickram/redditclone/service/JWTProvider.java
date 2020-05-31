package com.karthickram.redditclone.service;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class JWTProvider {

    public String generateToken(Authentication authentication) {

        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .compact();
    }

    public boolean validateToken(String jwt) {
        Jwts.parser().parseClaimsJws(jwt);
        return true;
    }
}
