package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.proxies.MsUserProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/identification")
public class LoginController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MsUserProxy msUserProxy;

    @GetMapping("/login")
    public String loginForm(HttpSession session, Model model){

        try {
            AppUserBean user = (AppUserBean) session.getAttribute("user");
            model.addAttribute("username", user.getFirstName());
        }catch (NullPointerException e){
            System.out.println("Pas d'utilisateur identifié");
        }

        logger.info("Essai d'affichage du formulaire de login");
        return "identification/login";
    }

    @GetMapping("/registration")
    public String registrationForm(){
        logger.info("Essai d'affichage du formulaire d'enregistrement");
        return "identification/registration";
    }

    @PostMapping("/login")
    public void login(@ModelAttribute AppUserBean user, Model model, HttpSession session){
        logger.info("Essai de login");
        logger.info("Appel de msUserProxy pour l'email : " + user.getEmail());


        AppUserBean appUser = msUserProxy.getUserByUsername(user.getEmail());

        if (appUser == null){
            logger.info("Pas d'utilisateur trouvé");
            model.addAttribute("error", "Cet utilisateur n'est pas enregistré");

        } else if (user.getPassword().equals(appUser.getPassword())){
            logger.info("Entrée de l'utilisateur dans la session");
            session.setAttribute("user", appUser);
            logger.info("vérification :" + session.getAttribute("user").toString());
        } else {
            logger.info("Mot de passe incorrect: " + user.getPassword() + " " + appUser.getPassword());
        }

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
