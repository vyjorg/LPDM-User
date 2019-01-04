/*
package com.lpdm.msuser.services;


import com.lpdm.msuser.msauthentication.AppUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Qualifier("main")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUserBean user = accountService.findUserByUserName(username);

        if(user == null)
            throw new UsernameNotFoundException(username);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(r->{
            ((ArrayList<GrantedAuthority>) authorities).add(new SimpleGrantedAuthority(r.getRoleName()));
        });

        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
 */