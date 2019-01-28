package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msproduct.ProductBean;
import com.lpdm.msuser.proxies.MsProductProxy;
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
    private SessionController sessionController;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * display the informations regarding the products(name, category, price, supplier, ...) and injects in the
     * @param id
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/{id}")
    public String productDescription(@PathVariable("id") int id, Model model, HttpSession session) {
        ProductBean product = msProductProxy.findProduct(id);
        model.addAttribute("product", product);
        sessionController.addSessionAttributes(session, model);
        return "products/productdescription";
    }

    /**
     * sends product to ms-product
     * @param product
     * @param model
     * @param session
     * @return
     */
    @PostMapping(value = "/")
    public String addProduct(@ModelAttribute ProductBean product, Model model, HttpSession session) {

        msProductProxy.addProduct(product);
        sessionController.addSessionAttributes(session, model);

        return "products/list";
    }

    /**
     * display the list of products
     * @param model
     * @param session
     * @return requested template
     */
    @GetMapping("/list/{category}")
    public String listProduct(Model model, HttpSession session) {
        List<ProductBean> products = msProductProxy.listProduct();
        model.addAttribute("products", products);
        List<ProductBean> cats = msProductProxy.listProduct();
        model.addAttribute("cats", cats);
        sessionController.addSessionAttributes(session, model);
        return "products/list";
    }

    /**
     * clear the cart and put the total back at 0,00
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/emptycart")
    public String emptyCart(HttpSession session, Model model) {
        sessionController.emptyCart();
        sessionController.addSessionAttributes(session, model);
        return "home";
    }

    /**
     * filter products by category and producer
     * @param category
     * @param producer
     * @param model
     * @return the template with matching products
     */
    @PostMapping("/sortbycatandprod")
    public String filterProducts(@RequestParam String category, @RequestParam String producer, Model model) {

        List<ProductBean> products = msProductProxy.listProductByCategory(Integer.parseInt(category));

        for(ProductBean product: products) {
            if (product.getProducer().getId() != Integer.parseInt(producer))
                products.remove(product);
        }

        model.addAttribute("products", products);

        return "home";
    }
}

