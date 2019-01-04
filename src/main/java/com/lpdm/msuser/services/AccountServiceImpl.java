package com.lpdm.msuser.services;

import com.lpdm.msuser.msauthentication.AppRoleBean;
import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.proxies.MsUserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MsUserProxy userProxy;

    @Override
    public AppUserBean saveUser(AppUserBean user) {
        String hashPW = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashPW);
        return userProxy.addUser(user);
    }

    @Override
    public AppRoleBean saveRole(@RequestBody AppRoleBean role) {
        return userProxy.addRole(role);
    }

    @Override
    public AppUserBean findUserByUserName(String userName) {
        return null; //userProxy.getUserByUsername(userName);
    }


    //@Override
    //public void addRoleToUse(String userName, String roleName) {
    //    AppRole role = appRoleRepository.findByRoleName(roleName);
    //    AppUser user = userProxy.findByUsername(userName);
    //    user.getRoles().add(role);
    //}

}
