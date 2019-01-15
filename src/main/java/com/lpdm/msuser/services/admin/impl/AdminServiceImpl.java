package com.lpdm.msuser.services.admin.impl;

import com.lpdm.msuser.msorder.OrderBean;
import com.lpdm.msuser.msorder.PaymentBean;
import com.lpdm.msuser.msproduct.ProductBean;
import com.lpdm.msuser.proxies.MsOrderProxy;
import com.lpdm.msuser.proxies.MsProductProxy;
import com.lpdm.msuser.services.admin.AdminService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final MsOrderProxy orderProxy;
    private final MsProductProxy productProxy;

    @Autowired
    public AdminServiceImpl(MsOrderProxy orderProxy, MsProductProxy productProxy) {
        this.orderProxy = orderProxy;
        this.productProxy = productProxy;
    }

    @Override
    public OrderBean findOrderById(int id) throws FeignException {
        return orderProxy.getOrderById(id);
    }

    @Override
    public List<OrderBean> findAllOrdersByUserId(int id) {
        return orderProxy.findAllByUserId(id);
    }

    @Override
    public List<OrderBean> findAllOrdersByUserEmail(String email) {
        return orderProxy.findAllByUserEmail(email);
    }

    @Override
    public List<OrderBean> findAllOrdersByUserLastName(String lastName) {
        return orderProxy.findAllByUserLastName(lastName);
    }

    @Override
    public OrderBean findOrderByInvoiceReference(String ref) {
        return orderProxy.findByInvoiceReference(ref);
    }

    @Override
    public List<PaymentBean> findAllPayment() {
        return orderProxy.getPaymentList();
    }

    @Override
    public ProductBean findProductById(int id) {
        return productProxy.findProduct(id);
    }
}
