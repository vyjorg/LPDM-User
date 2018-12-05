package com.lpdm.msuser.proxies;

import com.lpdm.msuser.msauthentication.AppUserBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
@FeignClient(name = "microservice-authentication", url = "localhost:28081")
public interface MsUserProxy {

    @GetMapping("/users")
    List<AppUserBean> getAllUsers();

    @GetMapping("/users/{id}")
    AppUserBean getUserById(@PathVariable("id") int id);
}
