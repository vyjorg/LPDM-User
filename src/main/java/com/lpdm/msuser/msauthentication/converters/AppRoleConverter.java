package com.lpdm.msuser.msauthentication.converters;


import com.lpdm.msuser.msauthentication.AppRoleBean;
import com.lpdm.msuser.proxies.MsUserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AppRoleConverter implements Converter<String, AppRoleBean> {

    @Autowired
    MsUserProxy userProxy;

    @Override
    public AppRoleBean convert(String roleId) {

        int id = Integer.parseInt(roleId);
        AppRoleBean appRoleBean = userProxy.getRoleById(id);

        return appRoleBean;
    }
}
