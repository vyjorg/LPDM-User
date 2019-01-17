package com.lpdm.msuser.msauthentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppRoleBean {

    private int id;

    private String roleName;

    private AppUserBean appUser;
}
