package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.proxies.MsUserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private MsUserProxy msUserProxy;

    @Autowired
    SessionController sessionController;

    @GetMapping("/list")
    public String allUsers(Model model, HttpSession session){
        List<AppUserBean> appUsers = msUserProxy.getAllUsers();
        model.addAttribute("users", appUsers);
        sessionController.addSessionAttributes(session, model);
        return "users/list";
    }

    @GetMapping("/{id}")
    public String userDescription(@PathVariable ("id") int id, Model model, HttpSession session){
        AppUserBean user = msUserProxy.getUserById(id);
        model.addAttribute("user", user);
        sessionController.addSessionAttributes(session, model);
        return "users/userdescription";
    }

    @PostMapping("/")
    public String addUser(@ModelAttribute AppUserBean user, Model model, HttpSession session){
        msUserProxy.addUser(user);
        sessionController.addSessionAttributes(session, model);
        return "users/list";
    }

}
