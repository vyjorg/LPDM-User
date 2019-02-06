package com.lpdm.msuser.controllers.producer;

import com.lpdm.msuser.controllers.SessionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ProducerController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionController sessionController;

    @GetMapping("/producer/product")
    public String loginForm(HttpSession session, Model model){
        log.info("Affichage du formulaire de login");
        sessionController.addSessionAttributes(session, model);
        return "shop/fragments/producer/product";
    }
}
