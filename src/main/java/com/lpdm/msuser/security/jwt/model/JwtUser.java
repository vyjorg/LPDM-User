package com.lpdm.msuser.security.jwt.model;

public class JwtUser {

    private int id;
    private String userName;
    private String role;

    public JwtUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
