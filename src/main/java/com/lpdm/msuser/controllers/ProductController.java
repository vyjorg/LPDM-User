package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msproduct.ProductBean;
import com.lpdm.msuser.proxies.MsProductProxy;
import com.lpdm.msuser.proxies.MsUserProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private MsProductProxy msProductProxy;

    @Autowired
    private MsUserProxy msUserProxy;

    @Autowired
    private SessionController sessionController;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public String productDescription(@PathVariable("id") int id, Model model, HttpSession session) {
        ProductBean product = msProductProxy.findProduct(id);
        model.addAttribute("product", product);
        sessionController.addSessionAttributes(session, model);
        return "products/productdescription";
    }


    @PostMapping(value = "/")
    public String addProduct(@ModelAttribute ProductBean product, Model model, HttpSession session) {

        System.out.println(product.toString());

        msProductProxy.addProduct(product);
        sessionController.addSessionAttributes(session, model);

        return "products/list";
    }

    @GetMapping("/list/{category}")
    public String listProduct(Model model, HttpSession session) {
        List<ProductBean> products = msProductProxy.listProduct();
        model.addAttribute("products", products);
        List<ProductBean> cats = msProductProxy.listProduct();
        model.addAttribute("cats", cats);
        sessionController.addSessionAttributes(session, model);
        return "products/list";
    }


    @GetMapping("/emptycart")
    public String emptyCart(HttpSession session, Model model) {
        sessionController.emptyCart();
        sessionController.addSessionAttributes(session, model);
        return "home";
    }

    @PostMapping("/sortbycatandprod")
    public String sortProducts(@RequestParam String category, Model model) {

        List<ProductBean> products = msProductProxy.listProductByCategory(Integer.parseInt(category));
        logger.info("Products.size " + products.size());
        model.addAttribute("products", products);
        return "home";
    }
}

