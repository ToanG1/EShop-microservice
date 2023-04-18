package com.example.UserService.jwt;

import com.example.UserService.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Provider for JWT Token Authorization
 */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Autowired
    private JwtConfigurationModel jwtConfigurationModel;

    public String generateToken(User userEntity) {
        Date now = new Date();
        Date expired = new Date(now.getTime() + this.jwtConfigurationModel.getExpired());
        return Jwts.builder()
                .setSubject(userEntity.getName())
                .setIssuedAt(now)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS512, this.jwtConfigurationModel.getSecret())
                .compact();
    }
}