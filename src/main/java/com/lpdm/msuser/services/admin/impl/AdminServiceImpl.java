package com.lpdm.msuser.services.admin.impl;

import com.lpdm.msuser.model.Store;
import com.lpdm.msuser.msorder.OrderBean;
import com.lpdm.msuser.msorder.PaymentBean;
import com.lpdm.msuser.msproduct.CategoryBean;
import com.lpdm.msuser.msproduct.ProductBean;
import com.lpdm.msuser.proxies.MsOrderProxy;
import com.lpdm.msuser.proxies.MsProductProxy;
import com.lpdm.msuser.proxies.MsStoreProxy;
import com.lpdm.msuser.services.admin.AdminService;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final MsOrderProxy orderProxy;
    private final MsProductProxy productProxy;
    private final MsStoreProxy storeProxy;

    @Qualifier("eurekaClient")
    @Autowired
    EurekaClient discoveryClient;

    @Autowired
    public AdminServiceImpl(MsOrderProxy orderProxy,
                            MsProductProxy productProxy,
                            MsStoreProxy storeProxy) {

        this.orderProxy = orderProxy;
        this.productProxy = productProxy;
        this.storeProxy = storeProxy;
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

    @Override
    public List<CategoryBean> findAllCategories() {
        return productProxy.findAllCotegories();
    }

    @Override
    public Store findStoreById(int id) throws FeignException {
        return storeProxy.findById(id);
    }

    @Override
    public List<Store> findStoreByName(String name) throws FeignException {
        return storeProxy.findByName(name);
    }

    @Override
    public List<Application> findAllApps() throws FeignException {

        return discoveryClient.getApplications().getRegisteredApplications();
    }
}
