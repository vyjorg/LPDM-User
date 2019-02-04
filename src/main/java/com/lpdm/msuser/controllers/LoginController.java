package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.msorder.OrderBean;
import com.lpdm.msuser.proxies.MsLocationProxy;
import com.lpdm.msuser.proxies.MsOrderProxy;
import com.lpdm.msuser.proxies.MsProductProxy;
import com.lpdm.msuser.proxies.MsUserProxy;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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

    @Autowired
    MsLocationProxy msLocationProxy;

    @Autowired
    MsOrderProxy msOrderProxy;

    /**
     * displays the login form
     * @param session
     * @param model
     * @return template
     */
    @GetMapping("/login")
    public String loginForm(HttpSession session, Model model){
        logger.info("Affichage du formulaire de login");
        sessionController.addSessionAttributes(session, model);
        return "shop/fragments/account/login";
    }

    /**
     * displays the registration form
     * @param session
     * @param model
     * @return template
     */
    @GetMapping("/registration")
    public String registrationForm(HttpSession session, Model model){
        logger.info("Affichage du formulaire d'enregistrement");
        return "shop/fragments/account/login";
    }

    /**
     * requests ms-auth to identify a user and retrieve their information if password matches. save records in the session
     * @param user
     * @param model
     * @param session
     * @return home template if correct credentials
     */
    @PostMapping("/login")
    public String login(@ModelAttribute AppUserBean user, Model model, HttpSession session){

        AppUserBean appUser = null;
        logger.info("appUser : " + user.toString());

        logger.info("trying to login");
        try {
            appUser = msUserProxy.login(user);
        }catch (FeignException e){
            model.addAttribute("error", "Nom d'utiisateur ou mot de passe invalide");
            sessionController.addSessionAttributes(session, model);
            return "shop/fragments/account/login";
        }


        if (appUser.getId() == 0){
            logger.info("No user found");
            model.addAttribute("error", " Cet utilisateur n'est pas enregistré");
            sessionController.addSessionAttributes(session, model);
            return "shop/fragments/account/login";

        } else {
            logger.info("appUser : " + appUser.toString());
            logger.info("Entering user in session");
            session.setAttribute("user", appUser);
            sessionController.addSessionAttributes(session, model);
            return "shop/fragments/home";
        }
    }

    /**
     * sends information to ms-auth to persist a new user and open an account after password confirmation. Save records in the session
     * @param name_reg
     * @param email_reg
     * @param password_reg
     * @param password_reg_2
     * @param model
     * @param session
    // * @param bindingResult
     * @return home template with user authenticated
     */
    @PostMapping("/registration")
    public String registration(@RequestParam String name_reg,
                               @RequestParam String email_reg,
                               @RequestParam String password_reg,
                               @RequestParam String password_reg_2,
                               Model model,
                               HttpSession session)//BindingResult bindingResult
    {

        AppUserBean user = new AppUserBean();
        user.setName(name_reg);
        user.setEmail(email_reg);
        user.setPassword(password_reg);

        logger.info("Essai de registration");
        logger.info("Utilisateur: " + user.getFirstName() + " " + user.getName() + " " + user.getEmail() + " " + user.getPassword());

        //if (bindingResult.hasErrors()) {
        //    return "shop/fragments/account/login";
        //}
        if (!user.getPassword().equals(password_reg_2)){
            model.addAttribute("error", "Les mots de passe sont différents");
        } else if (msUserProxy.getUserByEmail(user.getEmail()) != null) {
            model.addAttribute("error","L'utilisateur existe déjà");
        }else if(user.getPassword().equals(password_reg_2)){
            logger.info("Passwords match!");
            msUserProxy.addUser(user);
            session.setAttribute("user", user);
            sessionController.addSessionAttributes(session, model);
            return "shop/fragments/home";
        }
        sessionController.addSessionAttributes(session, model);

        return "shop/fragments/account/login";
    }

    /**
     * diconnects the user
     * @param session
     * @return home page template
     */
    @GetMapping("/logout")
    public String logout(HttpSession session, Model model){
        sessionController.addSessionAttributes(session, model);
        logger.info(OrderController.cart.toString());
        sessionController.logout(session);
        return "shop/fragments/home";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable ("id") int id, Model model, HttpSession session){
        logger.info("entering profile");
        AppUserBean user = msUserProxy.getUserById(id);
        model.addAttribute("user", user);
        logger.info("user:" +  user.toString());
        model.addAttribute("orders", msOrderProxy.findAllByUserId(id));
        session.setAttribute("user", user);
        sessionController.addSessionAttributes(session, model);
        return "shop/fragments/account/account";
    }

    @GetMapping("/edit/{id}")
    public String editProfileForm(@PathVariable("id") int id, Model model, HttpSession session){
        logger.info("entering profile");
        AppUserBean user = msUserProxy.getUserById(id);
        model.addAttribute("user", user);
        sessionController.addSessionAttributes(session, model);
        return "shop/fragments/account/account.html";
    }


    @PostMapping(value = "/edit")
    public String changeProfile(@ModelAttribute AppUserBean userToUpdate, Model model, HttpSession session){
        logger.info("changeProfile userToUpdate: " + userToUpdate.toString());
        AppUserBean sessionUser = (AppUserBean)session.getAttribute("user");
        userToUpdate.setId(sessionUser.getId());
        AppUserBean appUser = msUserProxy.updateAppUser(userToUpdate);
        model.addAttribute("user", appUser);
        sessionController.addSessionAttributes(session, model);
        return "shop/fragments/account/account.html";
    }

    @PostMapping(value = "/editroles")
    public String changeRoles(@RequestParam List<String> appRole, Model model, HttpSession session){
        AppUserBean sessionUser = (AppUserBean)session.getAttribute("user");

        sessionUser.getAppRole().clear();

        for(String role: appRole)
            sessionUser.getAppRole().add(msUserProxy.getRoleById(Integer.parseInt(role)));
        msUserProxy.updateAppUser(sessionUser);
        session.setAttribute("user", sessionUser);
        sessionController.addSessionAttributes(session, model);
        return "shop/fragments/account/account.html";
    }

}
