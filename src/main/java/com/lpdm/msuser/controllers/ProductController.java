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
import java.util.ArrayList;
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
     * displays the informations regarding the products(name, category, price, supplier, ...) and injects in the
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
        return "shop/fragments/products/description.html";
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
     * displays the list of products
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
     * clears the cart and put the total back at 0,00
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/emptycart")
    public String emptyCart(HttpSession session, Model model) {
        sessionController.emptyCart(session);
        sessionController.addSessionAttributes(session, model);
        return "shop/fragments/home";
    }

    /**
     * filters products by category and producer
     * @param category
     * @param producer
     * @param model
     * @return the template with matching products
     */
    @PostMapping("/sortbycatandprod")
    public String filterProducts(@RequestParam String category, @RequestParam String producer, Model model, HttpSession session) {

        logger.info("cat: " + category);
        logger.info("producer: " + producer);
        List<ProductBean> products = new ArrayList<>();

        if(!category.equals("0") && !producer.equals("0")) {
            products = msProductProxy.listProductByProducerId(Integer.parseInt(producer));
            products.removeIf(productBean -> productBean.getCategory().getId()!= Integer.parseInt(category));
            logger.info("!category.equals(\"0\") && !producer.equals(\"0\"): " + products.toString());

        }else if(producer.equals("0")) {
            products = msProductProxy.listProductByCategory(Integer.parseInt(category));
            logger.info("producer.equals(\"0\"): " + products.toString());

        }else if(category.equals("0")) {
            products = msProductProxy.listProductByProducerId(Integer.parseInt(producer));
            logger.info("category.equals(\"0\"): " + products.toString());
        }

        sessionController.addSessionAttributes(session, model);
        model.addAttribute("products", products);

        return "shop/fragments/home";
    }

    public static List<ProductBean> productFilter(int typeId, List<ProductBean> productBeanList){
        List<ProductBean> toBeDisplayed = new ArrayList<>();

        for (ProductBean p : productBeanList) {
            if (p.getId() == typeId)
                toBeDisplayed.add(p);
        }
        return toBeDisplayed;

    }
}

