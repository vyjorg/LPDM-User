package com.lpdm.msuser.controllers;

import com.lpdm.msuser.msorder.OrderBean;
import com.lpdm.msuser.msorder.OrderedProductBean;
import com.lpdm.msuser.msorder.PaymentBean;
import com.lpdm.msuser.proxies.MsOrderProxy;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private static final Logger log = Logger.getLogger(OrderController.class);

    @Autowired
    MsOrderProxy orderProxy;

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

}
