package ru.emin.security.jwt.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

@Component
public class JwtTokenUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims=new HashMap<>();
        List<String> roleList=userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        claims.put("roles",roleList);

        Date issuedDate=new Date();
        Date expiredDate=new Date(issuedDate.getTime()+jwtLifetime.toMillis());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.ES256,secret)
                .compact();
    }

    public List<String> getRoles(String token) {
        return getAllClaimsFromToken(token).get("roles",
                List.class);
    }

    public String getUsername(String token){
        return getAllClaimsFromToken(token).getSubject();
    }

    public Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret)
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            return false;
        }
    }

}
