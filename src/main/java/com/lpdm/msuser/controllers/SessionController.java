package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.proxies.MsAuthProxy;
import com.lpdm.msuser.proxies.MsProductProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;

/**
 * Manage all sesssion information
 */
public class SessionController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MsProductProxy msProductProxy;

    @Autowired
    MsAuthProxy msAuthProxy;

    @Bean
    public SessionController getSession(){
        return new SessionController();
    }

    /**
     * inserts all session data in the model
     * @param session
     * @param model
     */
    public void addSessionAttributes(HttpSession session, Model model){

        try {
            AppUserBean user = (AppUserBean) session.getAttribute("user");
            model.addAttribute("user", user);
        }catch (NullPointerException e){
            logger.info("Pas d'utilisateur identifié");
        }
        model.addAttribute("cart", OrderController.cart);
        model.addAttribute("total", OrderController.cartTotal);
        model.addAttribute("products", msProductProxy.listProduct());
        model.addAttribute("categories", msProductProxy.listCategories());
        model.addAttribute("roles", msAuthProxy.findAllRoles());

        //model.addAttribute("producers", )
    }

    /**
     * clears cart
     */
    public void emptyCart(){
        OrderController.cartTotal = 0;
        OrderController.cart.clear();
    }

    /**
     * clear all session data
     * @param session
     */
    public void logout(HttpSession session){

       session.removeAttribute("user");
        session.removeAttribute("cart");
        session.removeAttribute("total");


    }
}
