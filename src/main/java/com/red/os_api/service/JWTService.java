package com.red.os_api.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JWTService {

    @Value("${secret.key}")
    private static String KEY;

    public String getUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails){
       return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
               .signWith(getKey(), SignatureAlgorithm.HS512)
               .compact();
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsTFunction){
        final Claims claims = extractClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJwt(token).getBody();
    }

    private Key getKey(){
        byte[] key = Decoders.BASE64.decode(KEY);
        return Keys.hmacShaKeyFor(key);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername())) && isExpired(token);
    }

    private boolean isExpired(String token){
        return getExpirationDate(token).before(new Date());
    }

    private Date getExpirationDate(String token){
        return extractClaim(token,Claims::getExpiration);
    }
}
