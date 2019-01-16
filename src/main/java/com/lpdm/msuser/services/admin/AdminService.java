package com.lpdm.msuser.services.admin;

import com.lpdm.msuser.model.Store;
import com.lpdm.msuser.msorder.OrderBean;
import com.lpdm.msuser.msorder.PaymentBean;
import com.lpdm.msuser.msproduct.CategoryBean;
import com.lpdm.msuser.msproduct.ProductBean;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import feign.FeignException;

import java.util.List;

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
    List<CategoryBean> findAllCategories();

    /**
     * Store
     */
    Store findStoreById(int id) throws FeignException;
    List<Store> findStoreByName(String name) throws FeignException;

    /**
     * Eureka
     */
    List<Application> findAllApps() throws FeignException;

}
