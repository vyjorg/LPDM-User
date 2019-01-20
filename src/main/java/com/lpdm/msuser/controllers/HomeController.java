package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.proxies.MsProductProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class HomeController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MsProductProxy productProxy;

    @Autowired
    SessionController sessionController;


    @GetMapping("/")
    public String home(HttpSession session, Model model){
        logger.info("Entrée dans la méthode 'home'");
        sessionController.addSessionAttributes(session,model);
        model.addAttribute("products", productProxy.listProduct());
        return "home";
    }

}
