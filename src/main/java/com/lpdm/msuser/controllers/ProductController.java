package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msorder.OrderedProductBean;
import com.lpdm.msuser.msproduct.ProductBean;
import com.lpdm.msuser.proxies.MsProductProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/products")
public class ProductController{

    @Autowired
    private MsProductProxy msProductProxy;

    public static List<OrderedProductBean> cart = new ArrayList<>();

    public  static double cartTotal = 0;

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

   //@GetMapping("/{id}/add")
   //public String addItem(@PathVariable("id") int productId, Model model){

   //    logger.info("Entrée dans addItem pour produit : " + productId);

   //    OrderedProductBean orderedProduct = null;

   //    ProductBean product = msProductProxy.findProduct(productId);

   //    for (OrderedProductBean item: cart) {
   //        if (item.getProduct().getId() == productId) {
   //            orderedProduct = item;
   //            orderedProduct.setQuantity(orderedProduct.getQuantity() + 1);
   //            break;
   //        }
   //    }

   //    if (orderedProduct == null){
   //        orderedProduct = new OrderedProductBean();
   //        orderedProduct.setProduct(product);
   //        orderedProduct.setQuantity(1);
   //        cart.add(orderedProduct);
   //    }



  //     cartTotal += product.getPrice();

  //     model.addAttribute("product", product);
  //     model.addAttribute("cart", cart);
  //     model.addAttribute("products", msProductProxy.listProduct());
  //     model.addAttribute("total", cartTotal);


  //     return "home";
  // }

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
