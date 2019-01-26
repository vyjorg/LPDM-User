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
                //.addObject("productList", productService.findAllProducts());
    }
}
