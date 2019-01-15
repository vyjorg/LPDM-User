package com.lpdm.msuser.proxies;

import com.lpdm.msuser.msauthentication.AppUserBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Component
@FeignClient(name = "${lpdm.zuul.name}", url = "${lpdm.zuul.uri}")
@RibbonClient(name = "${lpdm.auth.name}")
public interface MsAuthProxy {

    @RequestMapping(path = "${lpdm.auth.name}/users/{id}",
            method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Optional<AppUserBean> findById(@PathVariable(value = "id") int id);

    @RequestMapping(path = "${lpdm.auth.name}/users/name/{name}",
            method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Optional<AppUserBean> findByLastName(@PathVariable(value = "name") String name);

    @RequestMapping(path = "${lpdm.auth.name}/users/email/{email}",
            method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Optional<AppUserBean> findByEmail(@PathVariable(value = "email") String name);
}
