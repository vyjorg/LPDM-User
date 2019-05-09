package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.msorder.OrderedProductBean;
import com.lpdm.msuser.proxies.MsAuthProxy;
import com.lpdm.msuser.proxies.MsProductProxy;
import com.lpdm.msuser.proxies.MsUserProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage all sesssion information
 */
public class SessionController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MsProductProxy msProductProxy;

    @Autowired
    MsAuthProxy msAuthProxy;

    @Autowired
    MsUserProxy msUserProxy;

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

        double cartTotal = 0;

        try {
            AppUserBean user = (AppUserBean) session.getAttribute("user");
            model.addAttribute("user", user);
        }catch (NullPointerException e){
            logger.info("Pas d'utilisateur identifié");
        }
        List<OrderedProductBean> orderedProducts = (List<OrderedProductBean>) session.getAttribute("cart");
        try {
             cartTotal = (double) session.getAttribute("cartTotal");
        }catch (NullPointerException e){
            logger.info("Le cart n'est pas initialisé");
        }

        model.addAttribute("cart", orderedProducts == null ? new ArrayList<OrderedProductBean>() : orderedProducts);
        model.addAttribute("producers", msUserProxy.getUsersByRole(3));
        model.addAttribute("total", cartTotal);
        model.addAttribute("products", msProductProxy.listProduct());
        model.addAttribute("categories", msProductProxy.listCategories());
        model.addAttribute("roles", msAuthProxy.findAllRoles());
    }

    /**
     * clears cart
     */
    public void emptyCart(HttpSession session){
        session.setAttribute("cartTotal", (double)0);
        session.setAttribute("cart", new ArrayList<OrderedProductBean>());
    }

    /**
     * clear all session data
     * @param session
     */
    public void logout(HttpSession session){
        session.setAttribute("cartTotal", (double)0);
        session.setAttribute("cart", new ArrayList<OrderedProductBean>());
        session.removeAttribute("user");
        session.removeAttribute("total");
        session.removeAttribute("cart");
    }
}
