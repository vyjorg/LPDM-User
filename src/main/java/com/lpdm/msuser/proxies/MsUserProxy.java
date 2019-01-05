package com.lpdm.msuser.proxies;

import com.lpdm.msuser.msauthentication.AppRoleBean;
import com.lpdm.msuser.msauthentication.AppUserBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Component
@FeignClient(name = "microservice-authentication", url = "localhost:28081")
public interface MsUserProxy {

    @GetMapping("/users/")
    List<AppUserBean> getAllUsers();

    @GetMapping("/users/{id}")
    AppUserBean getUserById(@PathVariable("id") int id);

    @PostMapping("/users")
    AppUserBean addUser(@RequestBody AppUserBean user);

    @PostMapping("/roles")
    AppRoleBean addRole(@RequestBody AppRoleBean role);

    @PostMapping("/users/get")
    AppUserBean getUserByUsername(@RequestParam String username);
}
