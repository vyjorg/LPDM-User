package com.lpdm.msuser.proxies;

import com.lpdm.msuser.msauthentication.AppRoleBean;
import com.lpdm.msuser.msauthentication.AppUserBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Component
@FeignClient(name = "zuul-server", url = "http://localhost:28090")
@RibbonClient(name = "microservice-users")
public interface MsUserProxy {

    @GetMapping("/microservice-users/users/")
    List<AppUserBean> getAllUsers();

    @GetMapping("/microservice-users/users/{id}")
    AppUserBean getUserById(@PathVariable("id") int id);

    @PostMapping("/microservice-users/users")
    AppUserBean addUser(@RequestBody AppUserBean user);

    @PostMapping("/microservice-users/roles")
    AppRoleBean addRole(@RequestBody AppRoleBean role);

    @PostMapping("/microservice-users/users/get")
    AppUserBean getUserByUsername(@RequestParam String username);
}
