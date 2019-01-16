package com.lpdm.msuser.msproduct;

import com.lpdm.msuser.msauthentication.AppRoleBean;
import com.lpdm.msuser.msauthentication.enumeration.Access;

import java.util.Collection;

public class ProducerBean {

        private int id;
        private String email;
        private String password;
        private Enum<Access> role;
        private Collection<AppRoleBean> roles;
        private String name;
        private String firstName;
        private String lastName;

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

        public Collection<AppRoleBean> getRoles() {
            return roles;
        }

        public void setRoles(Collection<AppRoleBean> roles) {
            this.roles = roles;
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

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }


}
