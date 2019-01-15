package com.lpdm.msuser.services.admin;

import com.lpdm.msuser.msorder.OrderBean;
import com.lpdm.msuser.msorder.PaymentBean;
import com.lpdm.msuser.msproduct.ProductBean;
import feign.FeignException;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    /**
     * Order
     */
    OrderBean findOrderById(int id) throws FeignException;
    List<OrderBean> findAllOrdersByUserId(int id);
    List<OrderBean> findAllOrdersByUserEmail(String email);
    List<OrderBean> findAllOrdersByUserLastName(String lastName);
    OrderBean findOrderByInvoiceReference(String ref);
    List<PaymentBean> findAllPayment();

    /**
     * Product
     */
    ProductBean findProductById(int id);
}
