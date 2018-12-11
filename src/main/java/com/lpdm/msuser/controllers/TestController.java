package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.msproduct.ProductBean;
import com.lpdm.msuser.proxies.MsProductProxy;
import com.lpdm.msuser.proxies.MsUserProxy;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TestController {

    private static final Logger log = Logger.getLogger(TestController.class);

    @Autowired
    private MsUserProxy msUserProxy;

    @Autowired
    MsProductProxy msProductProxy;

    @GetMapping("/test/test")
    public String test(Model model){
        log.info("Récupération des produits et des utilsateurs puis affichage: ");
        System.out.println("Récupération des produits et des utilsateurs puis affichage: ");
        List<ProductBean> products = msProductProxy.listProduct();
        model.addAttribute("products", products);


        List<AppUserBean> appUsers = msUserProxy.getAllUsers();
        model.addAttribute("users", appUsers);

        for (AppUserBean u: appUsers
             ) {
            System.out.println(u.getEmail());
        }
        return "test/test";
    }
}
