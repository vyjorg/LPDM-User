package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msauthentication.AppUserBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session, Model model){
        try {
            AppUserBean user = (AppUserBean) session.getAttribute("user");
            model.addAttribute("username", user.getFirstName());
        }catch (NullPointerException e){
            System.out.println("Pas d'utilisateur identifié");
        }
        return "home";
    }
}
