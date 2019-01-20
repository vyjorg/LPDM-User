package com.lpdm.msuser.services.admin.impl;

import com.lpdm.msuser.model.Storage;
import com.lpdm.msuser.model.Store;
import com.lpdm.msuser.model.admin.OrderStats;
import com.lpdm.msuser.model.admin.SearchDates;
import com.lpdm.msuser.model.admin.StorageUser;
import com.lpdm.msuser.msorder.OrderBean;
import com.lpdm.msuser.msorder.PaymentBean;
import com.lpdm.msuser.msproduct.CategoryBean;
import com.lpdm.msuser.msproduct.ProductBean;
import com.lpdm.msuser.proxies.MsOrderProxy;
import com.lpdm.msuser.proxies.MsProductProxy;
import com.lpdm.msuser.proxies.MsStorageProxy;
import com.lpdm.msuser.proxies.MsStoreProxy;
import com.lpdm.msuser.services.admin.AdminService;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final MsStorageProxy storageProxy;
    private final MsOrderProxy orderProxy;
    private final MsProductProxy productProxy;
    private final MsStoreProxy storeProxy;
    private final EurekaClient discoveryClient;

    @Autowired
    public AdminServiceImpl(MsOrderProxy orderProxy,
                            MsProductProxy productProxy,
                            MsStoreProxy storeProxy,
                            MsStorageProxy storageProxy,
                            @Qualifier("eurekaClient") EurekaClient discoveryClient) {

        this.orderProxy = orderProxy;
        this.productProxy = productProxy;
        this.storeProxy = storeProxy;
        this.discoveryClient = discoveryClient;
        this.storageProxy = storageProxy;
    }

    @Override
    public List<OrderBean> findOrderById(int id) throws FeignException {
        List<OrderBean> orderList = new ArrayList<>();
        orderList.add(orderProxy.getOrderById(id));
        return orderList;
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
    public List<OrderBean> findOrderByInvoiceReference(String ref) {
        List<OrderBean> orderList = new ArrayList<>();
        orderList.add(orderProxy.findByInvoiceReference(ref));
        return orderList;
    }

    @Override
    public List<PaymentBean> findAllPayment() {
        return orderProxy.getPaymentList();
    }

    @Override
    public OrderStats findOrderStatsByYear(Integer year) {
        return orderProxy.findOrderStatsByYear(year);
    }

    @Override
    public OrderStats getAverageStats(OrderStats stats1, OrderStats stats2) {

        OrderStats averageStats = new OrderStats();

        for(Map.Entry<Object, Object> entry : stats1.getDataStats().entrySet()){

            Integer value1 = (Integer) entry.getValue();
            Integer value2 = (Integer) stats2.getDataStats().get(entry.getKey());
            double average = (value1.doubleValue() + value2.doubleValue()) / 2;

            averageStats.getDataStats().put(entry.getKey(), average);
        }
        return averageStats;
    }

    @Override
    public List<OrderBean> findAllOrdersBetweenTwoDates(SearchDates dates) {
        log.info("Search dates : " + dates);
        return orderProxy.findAllOrdersBetweenDates(dates);
    }

    @Override
    public ProductBean findProductById(int id) {
        return productProxy.findProduct(id);
    }

    @Override
    public List<CategoryBean> findAllCategories() {
        return productProxy.listCategories();
    }

    @Override
    public List<ProductBean> findProductsByName(String name) {
        return productProxy.listProductByName(name);
    }

    @Override
    public List<ProductBean> findProductsByProducerId(int id) {
        return productProxy.listProductByProducerId(id);
    }

    @Override
    public OrderStats findOrderedProductsStatsByYear(int year) {

        return orderProxy.findOrderedProductsStatsByYear(year);
    }

    @Override
    public OrderStats findOrderedProductsStatsByYearAndCategory(int year) {

        return orderProxy.findOrderedProductsStatsByYearAndCategory(year);
    }

    @Override
    public String getUploadPictureForm(StorageUser user) {

        return storageProxy.getUploadForm(user);
    }

    @Override
    public void updateProduct(ProductBean product) {

        ProductBean oldProduct = productProxy.findProduct(product.getId());
        product.setListStock(oldProduct.getListStock());
        if(product.getPicture() == null) product.setPicture(oldProduct.getPicture());
        product.setProducerID(oldProduct.getProducer().getId());
        product.setProducer(oldProduct.getProducer());

        log.info("New Product : " + product.toString());

        productProxy.updateProduct(product);
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

    @Override
    public Storage findLatestFileUploadedByOwnerId(int id) {
        return storageProxy.getLatestFileUploadByOwner(id);
    }
}
