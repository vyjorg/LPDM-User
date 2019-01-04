package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msauthentication.AppUserBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/identification")
public class LoginController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/login")
    public String loginForm(){
        logger.info("Essai d'affichage du formulaire de login");
        return "identification/login";
    }

    @GetMapping("/registration")
    public String registrationForm(){
        logger.info("Essai d'affichage du formulaire d'enregistrement");
        return "identification/registration";
    }

    @PostMapping("/login")
    public void login(@ModelAttribute AppUserBean user){
        logger.info("Essai de login");
    }

    @PostMapping("/registration")
    public void registration(@ModelAttribute AppUserBean user, @RequestParam String password2){

        logger.info("Essai de registration");
        logger.info("Utilisateur: " + user.getFirstName() + " " + user.getName() + " " + user.getEmail() + " " + user.getPassword());
        if (user.getPassword().equals(password2)){
            logger.info("Mot de passe similaires");
        } else {
            logger.info("erreur de mdp: " + password2);
        }

    }
}
