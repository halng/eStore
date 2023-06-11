package com.e.store.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    //    TODO: Check why these two field is empty and null
    //    @Value("${e.store.jwt.secret}")
    private String jwtSecret = "HelloFromEStore";
    //    @Value("${e.store.jwt.token.expire}")
    private int jwtExpire = 3600000;

    public String generateAccessToken(String accountId, String roles) {
        Claims claims = Jwts.claims().setSubject(accountId);
        claims.put("roles", roles);
        Date duration = new Date((new Date()).getTime() + jwtExpire);

        return Jwts.builder().setClaims(claims).setIssuedAt(new Date()).setExpiration(duration)
            .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public String getAccountFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(token).getBody().getSubject();
    }

    public boolean validateToken(String token) throws Exception {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(token);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
