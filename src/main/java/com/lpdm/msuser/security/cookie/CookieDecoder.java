package com.lpdm.msuser.security.cookie;

import com.lpdm.msuser.security.jwt.auth.JwtValidator;
import com.lpdm.msuser.security.jwt.config.JwtAuthConfig;
import com.lpdm.msuser.security.jwt.model.JwtUser;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class CookieDecoder {

    private static JwtAuthConfig jwtAuthConfig;
    private static JwtValidator jwtValidator;

    @Autowired
    public CookieDecoder(JwtAuthConfig jwtAuthConfig, JwtValidator jwtValidator) {
        CookieDecoder.jwtAuthConfig = jwtAuthConfig;
        CookieDecoder.jwtValidator = jwtValidator;
    }

    public static JwtUser getJwtUser(HttpServletRequest request){

        if(request == null) return null;

        Cookie[] cookies = request.getCookies();
        if(cookies == null) return null;

        for(Cookie cookie : cookies){

            if(cookie.getName().equals(jwtAuthConfig.getHeader())){

                return jwtValidator.validate(cookie.getValue());
            }
        }

        return null;
    }
}
