package com.lpdm.msuser.services;

import com.lpdm.msuser.msauthentication.AppRoleBean;
import com.lpdm.msuser.msauthentication.AppUserBean;

import java.util.Optional;

public interface AccountService {
    AppUserBean saveUser(AppUserBean user);
    AppRoleBean saveRole(AppRoleBean role);

    //void addRoleToUse(String userName, String roleName);
    AppUserBean findUserByUserName(String userName);
}
