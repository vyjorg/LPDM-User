package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msauthentication.AppUserBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping("/")
    public String home(HttpSession session, Model model){

        logger.info("Entrée dans la méthode 'home'");

        try {
            AppUserBean user = (AppUserBean) session.getAttribute("user");
            logger.info(user.getEmail() + " identifié");
            model.addAttribute("username", user.getFirstName());
        }catch (NullPointerException e){
            System.out.println("Pas d'utilisateur identifié");
        }
        return "home";
    }
}
