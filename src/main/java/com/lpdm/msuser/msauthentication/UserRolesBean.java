package com.lpdm.msuser.msauthentication;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRolesBean {

    private int id;

    private AppRoleBean appRole;

    private AppUserBean appUser;

}
