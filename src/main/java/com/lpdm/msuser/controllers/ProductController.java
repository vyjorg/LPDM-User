package com.lpdm.msuser.controllers;


import com.lpdm.msuser.msproduct.CategoryBean;
import com.lpdm.msuser.msproduct.ProductBean;
import com.lpdm.msuser.proxies.MsProductProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController{

    @Autowired
    MsProductProxy msProductProxy;

    @GetMapping("/list")
    public String listProduct(Model model){
        List<ProductBean> products = msProductProxy.listProduct();
        model.addAttribute("products", products);
        List<ProductBean> cats = msProductProxy.listProduct();
        model.addAttribute("cats", cats);
        return "products/list";
    }

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
