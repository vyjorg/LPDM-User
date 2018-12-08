package com.lpdm.msuser.controllers;


import com.lpdm.msuser.msproduct.ProductBean;
import com.lpdm.msuser.proxies.MsProductProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    MsProductProxy msProductProxy;

    @GetMapping("/list")
    public String productList(Model model){
        List<ProductBean> products = msProductProxy.listProduct();
        model.addAttribute("products", products);
        return "products/list";
    }


}
