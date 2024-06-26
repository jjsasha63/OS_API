package com.red.os_api.service;

import com.nimbusds.jwt.JWTClaimsSet;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JwtService {

  @Value("${secret.key}")
  private String KEY;

//  private final JwtEncoder jwtEncoder;

  public String getUsername(String token) {
    return getClaim(token, Claims::getSubject);
  }


  private boolean isExpired(String token) {
    return getExpiration(token).before(new Date());
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  private Claims getClaims(String token) {
    return Jwts
            .parserBuilder()
            .setSigningKey(getReadyKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
  }

  public String generateToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails
  ) {
    return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
        .signWith(getReadyKey(), SignatureAlgorithm.HS512)
            .compact();
  }


//  public String generateToken(UserDetails userDetails){
//    Instant now = Instant.now();
//    String scope = userDetails.getAuthorities().stream()
//            .map(GrantedAuthority::getAuthority)
//            .collect(Collectors.joining(" "));
//    JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
//            .issuer("self")
//            .issuedAt(now)
//            .expiresAt(now.plus(6, ChronoUnit.HOURS))
//            .subject(userDetails.getUsername())
//            .claim("scope", scope)
//            .build();
//    return this.jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
//  }

  public boolean isValid(String token, UserDetails userDetails) {
    final String username = getUsername(token);
    return (username.equals(userDetails.getUsername())) && !isExpired(token);
  }

  private Date getExpiration(String token) {
    return getClaim(token, Claims::getExpiration);
  }


  private Key getReadyKey() {
    byte[] keyBytes = Decoders.BASE64.decode(KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getClaims(token);
    return claimsResolver.apply(claims);
  }



}
