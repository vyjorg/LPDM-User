package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.msorder.OrderBean;
import com.lpdm.msuser.msorder.OrderedProductBean;
import com.lpdm.msuser.msorder.PaymentBean;
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

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private static final Logger log = Logger.getLogger(OrderController.class);

    @Autowired
    MsOrderProxy orderProxy;

    @Autowired
    MsProductProxy msProductProxy;

    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    public static List<OrderedProductBean> cart = new ArrayList<>();

    public  static double cartTotal = 0;

    @GetMapping("/{id}")
    public String orderDescription(@PathVariable("id") int id, Model model){

        log.info("Demande de description du produit, appel duproxy des commandes pour obtenir la commande par l'id demandé dans le path");

        OrderBean order = orderProxy.getOrderById(id);
        model.addAttribute("order", order);

        List<OrderedProductBean> orderedProducts = order.getOrderedProducts();
        model.addAttribute("products", orderedProducts);

        log.info("Transfert des données de la commande vers la vue");

        return "orders/orderdescription";
    }

    @RequestMapping("/payments")
    public String getAllPayments(Model model) {
        List<PaymentBean> allPayments = orderProxy.getPaymentList();
        model.addAttribute("payments", allPayments);
        return "orders/payments";
    }

    @GetMapping("/save")
    public String saveOrder(Model model, HttpSession session){

        OrderBean order = new OrderBean();
        OrderBean orderConfirmation = new OrderBean();

        PaymentBean payment = orderProxy.getPaymentList().get(1);

        try {
            AppUserBean user = (AppUserBean) session.getAttribute("user");
            order.setCustomerId(user.getId());
        }catch (NullPointerException e){
            logger.info("L'utilisateur dpoit s'authentifier");
            return "identification/login";
        }

        List<OrderedProductBean> orderDetails = new ArrayList<>();

        orderDetails.addAll(cart);
        order.setTotal(cartTotal);
        order.setOrderedProducts(orderDetails);
        order.setStatus(StatusEnum.PROCESSED);
        order.setOrderDate(LocalDateTime.now());
        order.setPayment(payment);

        orderConfirmation = orderProxy.saveOrder(order);
        model.addAttribute("order", orderConfirmation);
        model.addAttribute("products", orderDetails);

        return "orders/orderdescription";
    }

    @GetMapping("/{id}/add")
    public String addItem(@PathVariable("id") int productId, Model model){

        logger.info("Entrée dans addItem pour produit : " + productId);

        OrderedProductBean orderedProduct = null;

        ProductBean product = msProductProxy.findProduct(productId);

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

        model.addAttribute("product", product);
        model.addAttribute("cart", cart);
        model.addAttribute("products", msProductProxy.listProduct());
        model.addAttribute("total", cartTotal);


        return "home";
    }


}
