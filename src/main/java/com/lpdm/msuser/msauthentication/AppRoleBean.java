package com.lpdm.msuser.msauthentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AppRoleBean {

    private int id;
    private String roleName;
    private AppUserBean appUser;

    public AppRoleBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public AppUserBean getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUserBean appUser) {
        this.appUser = appUser;
    }

    @Override
    public String toString() {
        return "AppRoleBean{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", appUser=" + appUser +
                '}';
    }
}
