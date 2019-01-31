package com.lpdm.msuser.security.jwt;

import com.lpdm.msuser.security.jwt.model.JwtAuthToken;
import com.lpdm.msuser.security.jwt.model.JwtUser;
import com.lpdm.msuser.security.jwt.model.JwtUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthProvider extends AbstractUserDetailsAuthenticationProvider {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final JwtValidator jwtValidator;

    @Autowired
    public JwtAuthProvider(JwtValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken userPassAuthToken)
            throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username,
                                       UsernamePasswordAuthenticationToken userPassAuthToken)

            throws AuthenticationException {

        JwtAuthToken jwtAuthToken = (JwtAuthToken) userPassAuthToken;
        String token = jwtAuthToken.getToken();

        log.info("Retrieve user");
        log.info("jwtAuthToken : " + jwtAuthToken);

        JwtUser jwtUser = jwtValidator.validate(token);

        if(jwtUser == null)
            throw new RuntimeException("Your token is not valid, do not play this game");

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(jwtUser.getRole()));
                //AuthorityUtils.commaSeparatedStringToAuthorityList(jwtUser.getRole());

        JwtUserDetails jwtUserDetails = new JwtUserDetails(jwtUser.getId(), jwtUser.getUserName(), token, grantedAuthorities);

        log.info("JwtUserDetails = " + jwtUserDetails);

        return jwtUserDetails;
    }

    @Override
    public boolean supports(Class<?> supportClass) {
        return (JwtAuthToken.class.isAssignableFrom(supportClass));
    }
}
