package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msauthentication.AppRoleBean;
import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.proxies.MsProductProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;


public class SessionController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MsProductProxy msProductProxy;

    @Bean
    public SessionController getSession(){
        return new SessionController();
    }

    public void addSessionAttributes(HttpSession session, Model model){

        List<AppRoleBean> appRoleBeans;

        try {
            AppUserBean user = (AppUserBean) session.getAttribute("user");
            model.addAttribute("username", user.getFirstName());
        }catch (NullPointerException e){
            logger.info("Pas d'utilisateur identifié");
        }
        model.addAttribute("cart", OrderController.cart);
        model.addAttribute("total", OrderController.cartTotal);
        model.addAttribute("products", msProductProxy.listProduct());

    }

    public void emptyCart(){
        OrderController.cartTotal = 0;
        OrderController.cart.clear();
    }

    public void logout(HttpSession session){

       session.removeAttribute("user");
        session.removeAttribute("cart");
        session.removeAttribute("total");


    }
}
