package com.lpdm.msuser.security.jwt.filter;

import com.lpdm.msuser.security.jwt.config.JwtAuthConfig;
import com.lpdm.msuser.security.jwt.model.JwtAuthToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthTokenFilter extends AbstractAuthenticationProcessingFilter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final JwtAuthConfig jwtConfig;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtAuthTokenFilter(JwtAuthConfig jwtConfig) {

        super("/admin/**");
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        log.info("Unsuccessful auth");
        log.info("-> " + failed.getMessage());
        super.unsuccessfulAuthentication(request, response, failed);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)

            throws AuthenticationException, IOException, ServletException {

        String jwtCookie = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0){
            for(Cookie cookie : cookies){
                log.info("Cookie : " + cookie.getName() + " -> " + cookie.getValue());
                if (cookie.getName().equals(jwtConfig.getHeader())){
                    jwtCookie = cookie.getValue();
                    break;
                }
            }
        }

        if(jwtCookie == null || !jwtCookie.startsWith(jwtConfig.getPrefix())) {
            log.warn("JWT Token is missing !");
            response.sendRedirect("/identification/login");
            return null;
        }

        String token = jwtCookie.replace(jwtConfig.getPrefix() + " ", "");

        log.info("JWT : " + token);
        JwtAuthToken jwtAuthToken = new JwtAuthToken(token);

        log.info("Authentication manager : " + authenticationManager.toString());
        return authenticationManager.authenticate(jwtAuthToken);
        //return getAuthenticationManager().authenticate(jwtAuthToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth)

            throws IOException, ServletException {

        super.successfulAuthentication(request, response, chain, auth);

        log.info("Successful Authentication method");

        chain.doFilter(request, response);
    }
}
