package com.lpdm.msuser.proxies;

import com.lpdm.msuser.msauthentication.AppRoleBean;
import com.lpdm.msuser.msauthentication.AppUserBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
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

    @PostMapping("/microservice-authentication/users")
    AppUserBean addUser(@RequestBody AppUserBean user);

    @PostMapping("/microservice-authentication/roles/")
    AppRoleBean addRole(@RequestBody AppRoleBean role);

    @PostMapping("/microservice-authentication/users/get")
    AppUserBean getUserByUsername(@RequestParam String username);

    @GetMapping("/microservice-authentication/users/email/{email}")
    AppUserBean getUserByEmail(@PathVariable String email);
}
