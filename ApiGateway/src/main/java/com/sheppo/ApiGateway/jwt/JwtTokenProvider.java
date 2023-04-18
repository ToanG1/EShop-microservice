package com.sheppo.ApiGateway.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

/**
 * Provider for JWT Token Authorization
 */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Autowired
    private JwtConfigurationModel jwtConfigurationModel;

    public Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(this.jwtConfigurationModel.getSecret())
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public Integer getUserIdFromToken(String jwtToken) {
        Claims claims = this.extractAllClaims(jwtToken);
        String subject = claims.getSubject();
        try {
            return Integer.parseInt(subject);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String extractUsername(String jwtToken) {
        return this.extractClaim(jwtToken, Claims::getSubject);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String jwtToken) {
        return this.extractClaim(jwtToken, Claims::getExpiration);
    }

    private boolean isTokenExpired(String jwtToken) {
        return this.extractExpiration(jwtToken).before(new Date());
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = this.extractUsername(jwtToken);
        return username.equals(userDetails.getUsername()) && !this.isTokenExpired(jwtToken);
    }
}