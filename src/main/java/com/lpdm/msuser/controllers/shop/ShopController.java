package com.lpdm.msuser.controllers.shop;

import com.lpdm.msuser.services.shop.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShopController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final ProductService productService;

    @Autowired
    public ShopController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/shop")
    public ModelAndView homePage(){

        log.info("-> Home page");
        return new ModelAndView("shop/fragments/home");
    }

    @GetMapping(value = "/shop/products")
    public ModelAndView productsPage(){

        log.info("-> Products page");
        return new ModelAndView("shop/fragments/products/index")
                .addObject("productCategories", productService.findAllProductCategories());
    }

    @GetMapping(value = "/shop/login")
    public ModelAndView loginPage(){

        log.info("-> Login page");
        return new ModelAndView("shop/fragments/account/login");
    }

    @GetMapping(value = "/shop/account")
    public ModelAndView accountPage(){

        log.info("-> Account page");
        return new ModelAndView("shop/fragments/account/account");
    }
}
