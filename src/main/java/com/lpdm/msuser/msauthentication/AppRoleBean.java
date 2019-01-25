package com.lpdm.msuser.msauthentication;

public class AppRoleBean {

    private int id;
    private String roleName;

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

    @Override
    public String toString() {
        return "AppRoleBean{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
