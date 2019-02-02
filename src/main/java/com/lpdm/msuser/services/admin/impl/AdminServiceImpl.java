package com.lpdm.msuser.services.admin.impl;

import com.lpdm.msuser.exception.EurekaInstanceNotFound;
import com.lpdm.msuser.model.location.Address;
import com.lpdm.msuser.model.location.City;
import com.lpdm.msuser.model.storage.Storage;
import com.lpdm.msuser.model.store.Store;
import com.lpdm.msuser.model.admin.OrderStats;
import com.lpdm.msuser.model.admin.SearchDates;
import com.lpdm.msuser.model.admin.StorageUser;
import com.lpdm.msuser.msauthentication.AppRoleBean;
import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.msorder.*;
import com.lpdm.msuser.msproduct.CategoryBean;
import com.lpdm.msuser.msproduct.ProductBean;
import com.lpdm.msuser.msproduct.StockBean;
import com.lpdm.msuser.proxies.*;
import com.lpdm.msuser.services.admin.AdminService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
    private final MsStockProxy stockProxy;
    private final MsAuthProxy authProxy;
    private final MsLocationProxy locationProxy;

    @Autowired
    public AdminServiceImpl(MsOrderProxy orderProxy,
                            MsProductProxy productProxy,
                            MsStoreProxy storeProxy,
                            MsStorageProxy storageProxy,
                            MsStockProxy stockProxy,
                            MsAuthProxy authProxy,
                            MsLocationProxy locationProxy,
                            @Qualifier("eurekaClient") EurekaClient discoveryClient) {

        this.orderProxy = orderProxy;
        this.productProxy = productProxy;
        this.storeProxy = storeProxy;
        this.discoveryClient = discoveryClient;
        this.storageProxy = storageProxy;
        this.stockProxy = stockProxy;
        this.authProxy = authProxy;
        this.locationProxy = locationProxy;
    }

    private List<String> calculateSubTotal(OrderBean order){

        double subTotal = 0;
        double subTax = 0;

        List<String> resultList = new ArrayList<>();
        for(OrderedProductBean orderedProduct : order.getOrderedProducts()){

            double productTotal = orderedProduct.getPrice() * orderedProduct.getQuantity();
            double taxTotal = productTotal * (orderedProduct.getTax() / 100);

            subTotal += productTotal;
            subTax += taxTotal;
        }

        subTotal = Math.round(subTotal * 100.0) / 100.0;
        subTax = Math.round(subTax * 100.0) / 100.0;

        double coupon = 0;
        if(order.getCoupon() != null) coupon = order.getCoupon().getAmount();

        double total = subTotal + subTax + coupon;

        DecimalFormat df = new DecimalFormat("0.00");

        resultList.add(df.format(subTotal));
        resultList.add(df.format(subTax));
        resultList.add(df.format(coupon));
        resultList.add(df.format(total));

        return resultList;
    }

    @Override
    public Map<OrderBean, List<String>> findOrderById(int id) throws FeignException {

        Map<OrderBean, List<String>> resultMap = new HashMap<>();

        OrderBean order = orderProxy.getOrderById(id);
        resultMap.put(order, calculateSubTotal(order));

        return resultMap;
    }

    @Override
    public Map<OrderBean, List<String>> findAllOrdersByUserId(int id) {

        List<OrderBean> orderList = orderProxy.findAllByUserId(id);
        Map<OrderBean, List<String>> resultMap = new HashMap<>();

        for(OrderBean order : orderList)
            resultMap.put(order, calculateSubTotal(order));

        return resultMap;
    }

    @Override
    public Map<OrderBean, List<String>> findAllOrdersByUserEmail(String email) {

        List<OrderBean> orderList = orderProxy.findAllByUserEmail(email);
        Map<OrderBean, List<String>> resultMap = new HashMap<>();

        for(OrderBean order : orderList)
            resultMap.put(order, calculateSubTotal(order));

        return resultMap;
    }

    @Override
    public Map<OrderBean, List<String>> findAllOrdersByUserLastName(String lastName) {

        List<OrderBean> orderList = orderProxy.findAllByUserLastName(lastName);
        Map<OrderBean, List<String>> resultMap = new HashMap<>();

        for(OrderBean order : orderList)
            resultMap.put(order, calculateSubTotal(order));

        return resultMap;
    }

    @Override
    public Map<OrderBean, List<String>> findOrderByInvoiceReference(String ref) {

        OrderBean order = orderProxy.findByInvoiceReference(ref);
        Map<OrderBean, List<String>> resultMap = new HashMap<>();
        resultMap.put(order, calculateSubTotal(order));

        return resultMap;
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
    public ProductBean addNewProduct(ProductBean product) {

        return productProxy.addProduct(product);
    }

    @Override
    public List<Coupon> findAllCoupons() {

        List<Coupon> couponList = null;

        try { couponList = orderProxy.getAllCoupons(); }
        catch (FeignException e){ log.warn(e.getMessage()); }

        return couponList;
    }

    @Override
    public Coupon addNewCoupon(Coupon coupon) {

        return orderProxy.addNewCoupon(coupon);
    }

    @Override
    public Coupon updateCoupon(Coupon coupon) {

        return orderProxy.updateCoupon(coupon);
    }

    @Override
    public boolean deleteCoupon(Coupon coupon) {

        return orderProxy.deleteCoupon(coupon);
    }

    @Override
    public List<Delivery> findAllDeliveryMethods() {

        List<Delivery> deliveryList = null;

        try { deliveryList = orderProxy.findAllDeliveryMethods(); }
        catch (FeignException e) { log.warn(e.getMessage()); }

        return deliveryList;
    }

    @Override
    public Delivery addNewDeliveryMethod(Delivery delivery) {

        return orderProxy.addNewDeliveryMethod(delivery);
    }

    @Override
    public Delivery updateDeliveryMethod(Delivery delivery) {

        return orderProxy.updateDeliveryMethod(delivery);
    }

    @Override
    public boolean deleteDeliveryMethod(Delivery delivery) {

        return orderProxy.deleteDeliveryMethod(delivery);
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
    public void deleteInstance(String appId, String instanceId) {

        Application app = discoveryClient.getApplication(appId);
        if(app == null) throw new EurekaInstanceNotFound();
        log.info("AppId : " + app.getName());
        log.info("Instance list : " );
        for(InstanceInfo inst : app.getInstances())
            log.info(" - " + inst.getId());

        InstanceInfo instance = app.getByInstanceId(instanceId);
        if(instance == null) throw new EurekaInstanceNotFound();
        log.info("InstanceId to remove : " + instance.getId());

        //instance.setIsDirty();

        log.info("Instance status before : " + instance.getStatus().toString());
        instance.setStatus(InstanceInfo.InstanceStatus.DOWN);
        log.info("Instance status after : " + instance.getStatus().toString());

        app.removeInstance(instance);
        log.info("Instance removed");
        for(InstanceInfo inst : app.getInstances())
            log.info(" - " + inst.getId());

        instance.setOverriddenStatus(InstanceInfo.InstanceStatus.OUT_OF_SERVICE);
        app.removeInstance(instance);
        log.info("Instance removed");
        for(InstanceInfo inst : app.getInstances())
            log.info(" - " + inst.getId());

        instance.setStatusWithoutDirty(InstanceInfo.InstanceStatus.OUT_OF_SERVICE);
        app.removeInstance(instance);
        log.info("Instance removed");
        for(InstanceInfo inst : app.getInstancesAsIsFromEureka())
            log.info(" - " + inst.getId());



        discoveryClient.getApplicationInfoManager().refreshDataCenterInfoIfRequired();


    }

    @Override
    public Storage findLatestFileUploadedByOwnerId(int id) {
        return storageProxy.getLatestFileUploadByOwner(id);
    }

    @Override
    public List<ProductBean> findStockById(int id) throws FeignException {

        StockBean stock = stockProxy.findStockById(id);
        ProductBean product = productProxy.findProduct(stock.getProductId());
        List<ProductBean> productList = new ArrayList<>();
        productList.add(product);

        return productList;
    }

    @Override
    public List<ProductBean> findStockByProductId(int id) {
        ProductBean product = productProxy.findProduct(id);
        List<ProductBean> productList = new ArrayList<>();
        productList.add(product);
        return productList;
    }

    @Override
    public List<ProductBean> findStockByProductName(String name) {

        return productProxy.listProductByName(name);
    }

    @Override
    public void deleteStockById(int id) {

        stockProxy.deleteStockById(id);
    }

    @Override
    public StockBean updateStock(StockBean stock) {
        return stockProxy.updateStock(stock);
    }

    @Override
    public StockBean addNewStock(StockBean stock) {
        return stockProxy.addNewStock(stock);
    }

    @Override
    public List<AppUserBean> findUserById(int id) {

        AppUserBean user = authProxy.findById(id);
        List<AppUserBean> userList = new ArrayList<>();
        userList.add(user);

        return userList;
    }

    @Override
    public List<AppUserBean> findUserByLastName(String lastName) {
        return authProxy.findByLastName(lastName);
    }

    @Override
    public List<AppRoleBean> findAllUserRoles() {
        return authProxy.findAllRoles();
    }

    @Override
    public AppUserBean addNewUser(AppUserBean user) {
        return authProxy.addNewUser(user);
    }

    @Override
    public List<AppUserBean> findUserByEmail(String email) {

        List<AppUserBean> userList = new ArrayList<>();
        userList.add(authProxy.findByEmail(email));
        return userList;
    }

    @Override
    public Integer getProducerRoleId() {

        List<AppRoleBean> roleList = authProxy.findAllRoles();
        for(AppRoleBean role : roleList){
            if(role.getRoleName().toLowerCase().equals("producer")){
                return role.getId();
            }
        }

        return null;
    }

    @Override
    public List<AppUserBean> findUserByIdAndRole(int userId, int roleId) {

        AppUserBean user = authProxy.findUserByIdAndRole(userId, roleId);
        List<AppUserBean> userList = new ArrayList<>();
        if(user != null) userList.add(user);
        return userList;
    }

    @Override
    public AppUserBean updateUser(AppUserBean user) {
        return authProxy.updateUser(user);
    }

    @Override
    public Address findAddressById(int id) {
        return locationProxy.findAddressById(id);
    }

    @Override
    public List<City> findCitiesByZipCode(String zipCode) {
        return locationProxy.findCitiesByZipCode(zipCode);
    }

    @Override
    public Address saveNewAddress(Address address, int userId) {

        address = locationProxy.saveNewAddress(address);

        AppUserBean user = authProxy.findById(userId);
        user.setAddressId(address.getId());

        authProxy.updateUser(user);
        return address;
    }
}
