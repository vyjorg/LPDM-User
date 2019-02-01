package com.lpdm.msuser.msauthentication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class AppUserBean {

    private int id;
    private String email;
    private String password;
    private List<AppRoleBean> appRole;
    private String name;
    private String firstName;
    private String tel;
    private LocalDate birthday;
    private LocalDateTime registrationDate;
    private Integer addressId;
    private Boolean active;

    public AppUserBean() {
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

    public List<AppRoleBean> getAppRole() {
        return appRole;
    }

    public void setAppRole(List<AppRoleBean> appRole) {
        this.appRole = appRole;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "AppUserBean{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", appRole=" + appRole +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", tel='" + tel + '\'' +
                ", birthday=" + birthday +
                ", registrationDate=" + registrationDate +
                ", addressId=" + addressId +
                ", active=" + active +
                '}';
    }
}
