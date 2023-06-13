package com.e.store.auth.config.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtilities {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access.expiration}")
    private Long   jwtExpiration;

    public <T> T extractClaim (String accessToken, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(accessToken);
        return claimResolver.apply(claims);
    }

    public Claims extractAllClaims (String accessToken) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(accessToken).getBody();
    }

    public String getUsernameFromAccessToken (String accessToken) {
        return extractClaim(accessToken, Claims::getSubject);
    }

    public Date extractExpiration (String accessToken) {
        return extractClaim(accessToken, Claims::getExpiration);
    }

    public Boolean isAccessTokenExpired (String accessToken) {
        return extractExpiration(accessToken).before(new Date());
    }

    public Boolean validateAccessToken (String accessToken, UserDetails userDetails) {
        final String username = getUsernameFromAccessToken(accessToken);
        return (username.equals(userDetails.getUsername())) && !isAccessTokenExpired(accessToken);
    }

    public String generateAccessToken (String username, String role) {
        return Jwts.builder().setSubject(username).claim("role", role).setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(Date.from(
                       Instant.now().plus(jwtExpiration, ChronoUnit.MILLIS))).signWith(SignatureAlgorithm.HS512, secret)
                   .compact();
    }

    public String getAccessToken (HttpServletRequest httpServletRequest) {
        final String bearerAccessToken = httpServletRequest.getHeader("authorization");
        if ( StringUtils.hasText(bearerAccessToken) && bearerAccessToken.startsWith("Bearer ") ) {
            return bearerAccessToken.substring(7);
        }
        return null;
    }

    public boolean validateAccessToken (String accessToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(accessToken);
            return true;
        }
        catch (SignatureException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
        }
        catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace: {}", e);
        }
        catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        }
        catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        }
        catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;

    }
}
