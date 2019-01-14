package com.lpdm.msuser.controllers.admin;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import org.slf4j.Logger;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = {"", "/", "/login", "/login/"})
    public ModelAndView adminHome(){
        log.info("URL : /admin/");
        return new ModelAndView("admin/fragments/login")
                .addObject("pageTitle", "Admin home");
    }

    @GetMapping(value = {"/orders", "/orders/"})
    public ModelAndView adminOrders(){
        log.info("Admin order");
        return new ModelAndView("admin/fragments/orders")
                .addObject("pageTitle","Admin orders");
    }

    @GetMapping(value = {"/orders/search", "/orders/search/"})
    public ModelAndView searchOrder(){
        return new ModelAndView("/admin/fragments/orders")
                .addObject("pageTitle", "Search order")
                .addObject("content", "searchPage");
    }

    @PostMapping(value = {"/orders/search", "/orders/search/"})
    public ModelAndView searchOrderResult(){
        return new ModelAndView("/admin/fragments/orders")
                .addObject("pageTitle", "Search order")
                .addObject("content", "searchPage")
                .addObject("result", "Un résultat");
    }
}
