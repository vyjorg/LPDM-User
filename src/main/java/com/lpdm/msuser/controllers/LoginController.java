package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.proxies.MsProductProxy;
import com.lpdm.msuser.proxies.MsUserProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/identification")
public class LoginController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MsUserProxy msUserProxy;

    @Autowired
    private SessionController sessionController;

    @Autowired
    MsProductProxy msProductProxy;

    @GetMapping("/login")
    public String loginForm(HttpSession session, Model model){
        logger.info("Affichage du formulaire de login");
        sessionController.addSessionAttributes(session, model);
        return "identification/login";
    }

    @GetMapping("/registration")
    public String registrationForm(HttpSession session, Model model){
        logger.info("Affichage du formulaire d'enregistrement");
        return "identification/registration";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute AppUserBean user, Model model, HttpSession session){
        logger.info("Essai de login");
        logger.info("Appel de msUserProxy pour l'email : " + user.getEmail());

        AppUserBean appUser = msUserProxy.getUserByEmail(user.getEmail());

        if (appUser == null){
            logger.info("Pas d'utilisateur trouvé");
            model.addAttribute("error", "Cet utilisateur n'est pas enregistré");
            return "identification/login";

        } else if (user.getPassword().equals(appUser.getPassword())){
            logger.info("Entrée de l'utilisateur dans la session");
            session.setAttribute("user", appUser);
            model.addAttribute("products", msProductProxy.listProduct());
            sessionController.addSessionAttributes(session, model);
            return "home";
        } else {
            logger.info("Mot de passe incorrect: " + user.getPassword() + " " + appUser.getPassword());
            return "identification/login";
        }

    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute AppUserBean user, @RequestParam String password2, Model model, HttpSession session, BindingResult bindingResult){

        logger.info("Essai de registration");
        logger.info("Utilisateur: " + user.getFirstName() + " " + user.getName() + " " + user.getEmail() + " " + user.getPassword());

        if (bindingResult.hasErrors()) {
            return "/identification/registration";
        }
        if (!user.getPassword().equals(password2)){
            logger.info("erreur de mdp: " + password2);
            model.addAttribute("error", "Les mots de passe saisis sont différents");
        } else if (msUserProxy.getUserByEmail(user.getEmail()) != null) {
            logger.info("L'utilisateur existe déjà");
            model.addAttribute("error","L'utilisateur existe déjà");
        }else if(user.getPassword().equals(password2)){
            logger.info("Mot de passe similaires");
            msUserProxy.addUser(user);
            return "home";
        }
        sessionController.addSessionAttributes(session, model);

        return "/identification/registration";
    }

}
