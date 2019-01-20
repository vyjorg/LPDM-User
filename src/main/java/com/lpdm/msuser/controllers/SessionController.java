package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msauthentication.AppUserBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;


public class SessionController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public SessionController getSession(){
        return new SessionController();
    }

    public void addSessionAttributes(HttpSession session, Model model){
        try {
            AppUserBean user = (AppUserBean) session.getAttribute("user");
            model.addAttribute("username", user.getFirstName());
        }catch (NullPointerException e){
            logger.info("Pas d'utilisateur identifié");
        }
        model.addAttribute("cart", ProductController.cart);

    }
}
