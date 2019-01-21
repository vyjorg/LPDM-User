package com.lpdm.msuser.msauthentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserBean {

    private int id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private Set<UserRolesBean> roles = new HashSet<>();

    private String name;

    private String firstName;

    private String tel;

    private LocalDate birthday;

    private LocalDateTime registrationDate;

    private int addressId;

    private Boolean active = true;
}
