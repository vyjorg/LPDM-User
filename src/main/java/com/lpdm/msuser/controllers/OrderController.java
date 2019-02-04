package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.msorder.*;
import com.lpdm.msuser.msorder.enumeration.StatusEnum;
import com.lpdm.msuser.msproduct.ProductBean;
import com.lpdm.msuser.proxies.MsOrderProxy;
import com.lpdm.msuser.proxies.MsProductProxy;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private static final Logger log = Logger.getLogger(OrderController.class);

    @Autowired
    MsOrderProxy orderProxy;

    @Autowired
    MsProductProxy msProductProxy;

    @Autowired
    SessionController sessionController;

    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    public static List<OrderedProductBean> cart = new ArrayList<>();

    public  static double cartTotal = 0;

    /**
     * gets a targeted order from ms-order and displays its description
     * @param id
     * @param model
     * @param session
     * @return template with order details
     */
    @GetMapping("/{id}")
    public String orderDescription(@PathVariable("id") int id, Model model, HttpSession session){

        log.info("Demande de description du produit, appel duproxy des commandes pour obtenir la commande par l'id demandé dans le path");

        OrderBean order = orderProxy.getOrderById(id);
        model.addAttribute("order", order);

        List<OrderedProductBean> orderedProducts = order.getOrderedProducts();
        model.addAttribute("products", orderedProducts);

        log.info("Transfert des données de la commande vers la vue");
        sessionController.addSessionAttributes(session, model);

        return "shop/fragments/cart/view";
    }

    /**
     * displays a list of payments
     * @param model
     * @param session
     * @return the template with list of payments
     */
    @RequestMapping("/payments")
    public String getAllPayments(Model model, HttpSession session) {
        List<PaymentBean> allPayments = orderProxy.getPaymentList();
        model.addAttribute("payments", allPayments);
        sessionController.addSessionAttributes(session, model);
        return "orders/payments";
    }

    /**
     * requests ms-order to persist an order made by authenticated user
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/save")
    public String saveOrder(Model model, HttpSession session){

        OrderBean order = new OrderBean();
        PaymentBean payment = orderProxy.getPaymentList().get(1);

        try {
            AppUserBean user = (AppUserBean) session.getAttribute("user");
            order.setCustomer(user);
            order.setCustomerId(user.getId());
        }catch (NullPointerException e){
            logger.info("L'utilisateur doit s'authentifier");
            sessionController.addSessionAttributes(session, model);
            return "shop/fragments/account/login";
        }

        order.setTotal(cartTotal);
        order.setOrderedProducts(cart);
        order.setStatus(StatusEnum.PROCESSED);
        order.setOrderDate(LocalDateTime.now());
        order.setPayment(payment);

        OrderBean orderConfirmation = orderProxy.saveOrder(order);

        model.addAttribute("order", orderConfirmation);
        model.addAttribute("products", orderConfirmation.getOrderedProducts());

        sessionController.addSessionAttributes(session, model);
        return "shop/fragments/cart/view";
    }

    /**
     * adds a targeted product in the cart and update the cart total
     * @param productId
     * @param model
     * @param session
     * @return template home template with updated information
     */
    @GetMapping("/{id}/add")
    public String addItem(@PathVariable("id") int productId, Model model, HttpSession session){

        logger.info("Entrée dans addItem pour produit : " + productId);

        OrderedProductBean orderedProduct = null;

        ProductBean product = msProductProxy.findProduct(productId);

        // check stock quantity available

        for (OrderedProductBean item: cart) {
            if (item.getProduct().getId() == productId) {
                orderedProduct = item;
                orderedProduct.setQuantity(orderedProduct.getQuantity() + 1);
                break;
            }
        }
        if (orderedProduct == null){
            orderedProduct = new OrderedProductBean();
            orderedProduct.setProduct(product);
            orderedProduct.setQuantity(1);
            cart.add(orderedProduct);
        }

        cartTotal += product.getPrice();
        logger.info("total panier + :" + cartTotal);


        model.addAttribute("product", product);
        model.addAttribute("products", msProductProxy.listProduct());

        sessionController.addSessionAttributes(session, model);

        return "shop/fragments/home";
    }

    /**
     * substracts a targeted product in the cart and update the cart total
     * @param productId
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/{id}/sub")
    public String subItem(@PathVariable("id") int productId, Model model, HttpSession session){

        logger.info("Entrée dans addItem pour produit : " + productId);

        OrderedProductBean orderedProduct = null;
        ProductBean product = null;

        product = msProductProxy.findProduct(productId);

        logger.info("product: " + product);

        for (OrderedProductBean item : cart) {
            if (item.getProduct().getId() == productId) {
                orderedProduct = item;
                orderedProduct.setQuantity(orderedProduct.getQuantity() >= 1 ? orderedProduct.getQuantity() - 1 : 0);
                if (orderedProduct.getQuantity() == 0)
                    cart.remove(orderedProduct);
                break;
            }
        }
        logger.info("après la boucle");
        if (orderedProduct != null)
            cartTotal = orderedProduct.getQuantity() >= 0 ? cartTotal -= product.getPrice() : cartTotal;

        logger.info("total panier - :" + cartTotal);

        model.addAttribute("products", msProductProxy.listProduct());
        sessionController.addSessionAttributes(session, model);
        return "shop/fragments/home";
    }

    @GetMapping("/paypalsuccess")
    public String successPaypal(){
        return "orders/paypalsuccess";
    }

    @GetMapping("/paypalcancel")
    public String cancelPaypal(){
        return "orders/paypalcancel";
    }


    @GetMapping("/confirmorder/{id}")
    public String confirmOrder(@PathVariable("id") int id, HttpServletResponse response){

        SuccessUrl successUrl = new SuccessUrl();
        successUrl.setReturnUrl("https://lpdm.kybox.fr/orders/paypalsuccess");
        successUrl.setCancelUrl("https://lpdm.kybox.fr/orders/paypalcancel");

        PaypalUrl paypalUrl = orderProxy.getPayPalUrl(id, successUrl);

        for (Map.Entry<String, String> header: paypalUrl.getHeaders().entrySet()) {
            response.setHeader(header.getKey(), header.getValue());
        }


        return "redirect:" + paypalUrl.getRedirectUrl();
    }

    @GetMapping("/description/{id}")
    public String orderDescription(@PathVariable ("id") int id, HttpSession session, Model model){

        OrderBean order = orderProxy.getOrderById(id);
        model.addAttribute("order", order);
        sessionController.addSessionAttributes(session, model);
        return "shop/fragments/cart/view.html";

    }






}
