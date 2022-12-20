package com.example.personalproject.JwtTokenUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil{

    public static String createToken(String key, Long expiredTime){
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

}
