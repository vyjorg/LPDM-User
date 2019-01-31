package com.lpdm.msuser.controllers;

import com.lpdm.msuser.proxies.MsProductProxy;
import com.lpdm.msuser.proxies.MsUserProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MsProductProxy productProxy;

    @Autowired
    MsUserProxy userProxy;

    @Autowired
    SessionController sessionController;

    /**
     * displays home page
     * @param model
     * @return
     */
    @GetMapping("/")
    public String home(Model model){
        logger.info("Entrée dans la méthode 'home'");
        //sessionController.addSessionAttributes(session,model);
        model.addAttribute("categories", productProxy.listCategories());
        model.addAttribute("producers", userProxy.getUsersByRole(3));
        return "home";
    }

}
