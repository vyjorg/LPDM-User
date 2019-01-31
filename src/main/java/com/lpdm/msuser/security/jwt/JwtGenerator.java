package com.lpdm.msuser.security.jwt;

import com.lpdm.msuser.security.jwt.config.JwtAuthConfig;
import com.lpdm.msuser.security.jwt.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class JwtGenerator {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final JwtAuthConfig jwtConfig;

    @Autowired
    public JwtGenerator(JwtAuthConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generate(JwtUser jwtUser){

        log.info("Generate token with the signing key : " + jwtConfig.getSecret());

        Claims claims = Jwts.claims().setSubject(jwtUser.getUserName());
        claims.put("id", jwtUser.getId());
        claims.put("role", jwtUser.getRole());

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(jwtConfig.getExpiration())))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                .compact();

        return jwtConfig.getPrefix() + " " + token;
    }
}
