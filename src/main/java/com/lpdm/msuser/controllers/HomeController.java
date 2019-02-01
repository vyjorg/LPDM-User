package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msproduct.ProductBean;
import com.lpdm.msuser.proxies.MsProductProxy;
import com.lpdm.msuser.proxies.MsUserProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/")
    public String home(HttpSession session, Model model){
        logger.info("Entrée dans la méthode 'home'");
        sessionController.addSessionAttributes(session,model);
        model.addAttribute("categories", productProxy.listCategories());
        model.addAttribute("producers", userProxy.getUsersByRole(3));
        model.addAttribute( "products", productToBeDisplayed(productProxy.listProduct(), 2, 3));

        return "home";
    }

    @PostMapping("/message")
    public String message(@RequestParam String email, @RequestParam String text, HttpSession session, Model model){
        logger.info(text + " de " + email);
        return "home";
    }

    /**
     * select the products to be displayed at requested page
     * @param productList
     * @param page
     * @param prodPerPage
     * @return list of products to be displayed
     */
    public List<ProductBean> productToBeDisplayed(List<ProductBean> productList, int page, int prodPerPage){
        List<ProductBean> toBeDisplayed = new ArrayList<>();
        int startIndex = page * prodPerPage;

        for(int i = startIndex; i < startIndex + prodPerPage; i++)
            toBeDisplayed.add(productList.get(i));

        logger.info(toBeDisplayed.toString());

        return toBeDisplayed;

    }

}
