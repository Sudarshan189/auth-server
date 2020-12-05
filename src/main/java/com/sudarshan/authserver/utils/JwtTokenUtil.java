package com.sudarshan.authserver.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Value("${jwt.sudarshan.secrete}")
    private String secrete;

    @Value("${jwt.sudarshan.tokenvalidity}")
    private int tokenValidity;

    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaimsFromJwt(token);
        return claimsResolver.apply(claims);
    }

    public Date getExpirationDate(String token) {
        Date experationDate = getClaimsFromToken(token, Claims::getExpiration);
        return  experationDate;
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getClaimsFromJwt(String token) {
        return Jwts.parser().setSigningKey(secrete).parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token, Claims::getSubject);
    }

    public String generateJwtToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", "newsapp");
        return doGenerateJwtToken(claims, userDetails.getUsername());
    }

    private boolean isTokenExpired(String token) {
        Date tokenExperation = getExpirationDate(token);
        return tokenExperation.before(new Date());
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateJwtToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidity))
                .signWith(SignatureAlgorithm.HS512, secrete).compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return userDetails.getUsername().equals(username) && !isTokenExpired(token);
    }
}
