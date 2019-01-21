package com.lpdm.msuser.msauthentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppRoleBean {

    private int id;

    private String roleName;

    private Set<UserRolesBean> roles = new HashSet<>();

}
