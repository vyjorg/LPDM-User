package com.lpdm.msuser.controllers.producer;

import com.lpdm.msuser.controllers.SessionController;
import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.msproduct.CategoryBean;
import com.lpdm.msuser.proxies.MsProductProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProducerController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionController sessionController;

    @Autowired
    private MsProductProxy msProductProxy;

    @GetMapping("/producer/product")
    public String addProductForm(HttpSession session, Model model){
        log.info("Affichage du formulaire d'ajout de produit");

        AppUserBean user = (AppUserBean) session.getAttribute("user");
        List<CategoryBean> listCategory = msProductProxy.listCategories();
        model.addAttribute("user",user);
        model.addAttribute("listCategory",listCategory);
        sessionController.addSessionAttributes(session, model);
        return "shop/fragments/producer/product";
    }
}
