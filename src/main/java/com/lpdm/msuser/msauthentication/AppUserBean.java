package com.lpdm.msuser.msauthentication;

import com.lpdm.msuser.msauthentication.enumeration.Access;


public class AppUserBean {

    private int id;

    private String email;

    private String password;

    private Enum<Access> role;

    private String name;
    private String firstName;

    private AddressBean address;

    public AppUserBean() {
    }

    public AppUserBean(String email, String password, Enum<Access> role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public AppUserBean(String email, String password, Enum<Access> role, String name, String firstName, AddressBean address) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.firstName = firstName;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Enum<Access> getRole() {
        return role;
    }

    public void setRole(Enum<Access> role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public AddressBean getAddressBean() {
        return address;
    }

    public void setAddressBean(AddressBean address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", address=" + address +
                '}';
    }
}
