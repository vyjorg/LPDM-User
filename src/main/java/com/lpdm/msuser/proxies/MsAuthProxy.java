package com.lpdm.msuser.proxies;

import com.lpdm.msuser.msauthentication.AppRoleBean;
import com.lpdm.msuser.msauthentication.AppUserBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@Component
@FeignClient(name = "${lpdm.zuul.name}", url = "${lpdm.zuul.uri}")
@RibbonClient(name = "${lpdm.auth.name}")
public interface MsAuthProxy {

    @RequestMapping(path = "${lpdm.auth.name}/users/{id}",
            method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    AppUserBean findById(@PathVariable(value = "id") int id);

    @RequestMapping(path = "${lpdm.auth.name}/users/username/{name}",
            method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<AppUserBean> findByLastName(@PathVariable(value = "name") String name);

    @RequestMapping(path = "${lpdm.auth.name}/users/email/{email}",
            method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    AppUserBean findByEmail(@PathVariable(value = "email") String name);

    @RequestMapping(path = "${lpdm.auth.name}/roles/",
            method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<AppRoleBean> findAllRoles();

    @RequestMapping(path = "${lpdm.auth.name}/users/",
            method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    AppUserBean addNewUser(@RequestBody AppUserBean user);

    @RequestMapping(path = "${lpdm.auth.name}/roles/check/{userId}/{roleId}",
            method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    AppUserBean findUserByIdAndRole(@PathVariable(value = "userId") int userId,
                                    @PathVariable(value = "roleId") int roleId);
}
