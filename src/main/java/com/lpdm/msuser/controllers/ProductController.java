package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.msorder.OrderedProductBean;
import com.lpdm.msuser.msproduct.CategoryBean;
import com.lpdm.msuser.msproduct.ProducerBean;
import com.lpdm.msuser.msproduct.ProductBean;
import com.lpdm.msuser.proxies.MsAuthProxy;
import com.lpdm.msuser.proxies.MsProductProxy;
import com.lpdm.msuser.proxies.MsUserProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/products")
public class ProductController{

    @Autowired
    private MsProductProxy msProductProxy;

    @Autowired
    private MsUserProxy msUserProxy;

    @Autowired
    private SessionController sessionController;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public String productDescription(@PathVariable("id") int id, Model model, HttpSession session){
        ProductBean product = msProductProxy.findProduct(id);
        model.addAttribute("product", product);
        sessionController.addSessionAttributes(session, model);
        return "products/productdescription";
    }


    @PostMapping(value = "/")
    public String addProduct(@ModelAttribute ProductBean product, Model model, HttpSession session){

        System.out.println(product.toString());

        msProductProxy.addProduct(product);
        sessionController.addSessionAttributes(session, model);

        return "products/list";
    }

    @GetMapping("/list/{category}")
    public String listProduct(Model model, HttpSession session){
        List<ProductBean> products = msProductProxy.listProduct();
        model.addAttribute("products", products);
        List<ProductBean> cats = msProductProxy.listProduct();
        model.addAttribute("cats", cats);
        sessionController.addSessionAttributes(session, model);
        return "products/list";
    }

    @PostMapping("/sortbycatandprod")
    public String sortProducts(@RequestParam String category, HttpSession session, Model model){

        List<ProductBean> products = msProductProxy.listProductByCategory(Integer.parseInt(category));
        logger.info("Products.size " + products.size());
        model.addAttribute("products", products);
        return "home";
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
