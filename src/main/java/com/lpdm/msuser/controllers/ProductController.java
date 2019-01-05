package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msproduct.ProductBean;
import com.lpdm.msuser.proxies.MsProductProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/products")
public class ProductController{

    @Autowired
    private MsProductProxy msProductProxy;

    public static Map<String, Integer> cart = new HashMap();

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public String productDescription(@PathVariable("id") int id, Model model){
        ProductBean product = msProductProxy.findProduct(id);
        model.addAttribute("product", product);
        return "products/productdescription";
    }


    @PostMapping(value = "/")
    public String addProduct(@ModelAttribute ProductBean product){

        System.out.println(product.toString());

        msProductProxy.addProduct(product);

        return "products/list";
    }

    @GetMapping("/list/{category}")
    public String listProduct(Model model){
        List<ProductBean> products = msProductProxy.listProduct();
        model.addAttribute("products", products);
        List<ProductBean> cats = msProductProxy.listProduct();
        model.addAttribute("cats", cats);
        return "products/list";
    }

    @GetMapping("/{id}/add")
    public String addItem(@PathVariable("id") int productId, Model model){
        int q = 0;

        logger.info("Entrée dans addItem pour produit : " + productId);
        ProductBean product = msProductProxy.findProduct(productId);

        cart.put(product.getName(), cart.get(product.getName()) == null ? 1 : cart.get(product.getName()) + 1);

        model.addAttribute("cart", cart);
        model.addAttribute("products", msProductProxy.listProduct());

        return "home";
    }

    /*
    @PostMapping("/")
    public String addUser(@ModelAttribute AppUserBean user){

        msUserProxy.addUser(user);

        return "users/list";
    }


    @PostMapping(value = "/products")
    void addProduct(@RequestBody ProductBean product);

   @PostMapping("/users")
    void addUser(@RequestBody AppUserBean user);

     */

}
