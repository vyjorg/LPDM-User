package com.lpdm.msuser.proxies;

import com.lpdm.msuser.msauthentication.AppRoleBean;
import com.lpdm.msuser.msauthentication.AppUserBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(name = "zuul-server", url = "https://zuul.lpdm.kybox.fr")
@RibbonClient(name = "microservice-authentication")
public interface MsUserProxy {

    @GetMapping("/microservice-authentication/users/")
    List<AppUserBean> getAllUsers();

    @GetMapping("/microservice-authentication/users/{id}")
    AppUserBean getUserById(@PathVariable("id") int id);

    @PostMapping(value = "/microservice-authentication/users/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    AppUserBean login(@RequestBody AppUserBean user);

    @PostMapping("/microservice-authentication/users/")
    AppUserBean addUser(@RequestBody AppUserBean user);

    @PostMapping("/microservice-authentication/roles/")
    AppRoleBean addRole(@RequestBody AppRoleBean role);

    @PostMapping("/microservice-authentication/users/get")
    AppUserBean getUserByUsername(@RequestParam String username);

    @GetMapping("/microservice-authentication/users/email/{email}")
    AppUserBean getUserByEmail(@PathVariable String email);

    @GetMapping(value = "/microservice-authentication/users/per_role/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<AppUserBean>getUsersByRole(@PathVariable("id") int id);

    @GetMapping(value = "/microservice-authentication/roles/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    AppRoleBean getRoleById(@PathVariable("id") int id);

    @PutMapping(value = "/microservice-authentication/users/updateuser", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    AppUserBean updateAppUser(@RequestBody AppUserBean user);

    @PutMapping(value = "/microservice-authentication/password/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Boolean updatePassword(@PathVariable("id") int id, @RequestParam String oldPassword, @RequestParam String newPassword);
}
