package com.lpdm.msuser.security.jwt;

import com.lpdm.msuser.security.jwt.config.JwtAuthConfig;
import com.lpdm.msuser.security.jwt.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private JwtAuthConfig jwtAuthConfig;

    @Autowired
    public JwtValidator(JwtAuthConfig jwtAuthConfig) {
        this.jwtAuthConfig = jwtAuthConfig;
    }

    public JwtUser validate(String token){

        log.info("Jwt validator");
        log.info("Token : " + token);
        log.info("Secret : " + jwtAuthConfig.getSecret());

        JwtUser jwtUser = null;

        try{

            Claims tokenBody = Jwts.parser()
                    .setSigningKey(jwtAuthConfig.getSecret())
                    .parseClaimsJws(token).getBody();

            jwtUser = new JwtUser();
            jwtUser.setUserName(tokenBody.getSubject());
            jwtUser.setId((Integer) tokenBody.get("id"));
            jwtUser.setRole(String.valueOf(tokenBody.get("role")));

            log.info("JwtUser : " + jwtUser);
        }
        catch (Exception e) { log.error(e.getMessage()); }

        return jwtUser;
    }
}
