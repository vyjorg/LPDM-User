package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.proxies.MsUserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private MsUserProxy msUserProxy;

    @GetMapping("/list")
    public String allUsers(Model model){
        List<AppUserBean> appUsers = msUserProxy.getAllUsers();
        model.addAttribute("users", appUsers);
        return "users/list";
    }
}
