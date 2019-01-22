package com.lpdm.msuser.services.admin;

import com.lpdm.msuser.model.Storage;
import com.lpdm.msuser.model.Store;
import com.lpdm.msuser.model.admin.OrderStats;
import com.lpdm.msuser.model.admin.SearchDates;
import com.lpdm.msuser.model.admin.StorageUser;
import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.msorder.OrderBean;
import com.lpdm.msuser.msorder.PaymentBean;
import com.lpdm.msuser.msproduct.CategoryBean;
import com.lpdm.msuser.msproduct.ProductBean;
import com.lpdm.msuser.msproduct.StockBean;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import feign.FeignException;

import java.time.LocalDate;
import java.util.List;

public interface AdminService {

    /**
     * Order
     */
    List<OrderBean> findOrderById(int id) throws FeignException;
    List<OrderBean> findAllOrdersByUserId(int id);
    List<OrderBean> findAllOrdersByUserEmail(String email);
    List<OrderBean> findAllOrdersByUserLastName(String lastName);
    List<OrderBean> findOrderByInvoiceReference(String ref);
    List<PaymentBean> findAllPayment();
    OrderStats findOrderStatsByYear(Integer year);
    OrderStats getAverageStats(OrderStats stats1, OrderStats stats2);
    List<OrderBean> findAllOrdersBetweenTwoDates(SearchDates dates);

    /**
     * Product
     */
    ProductBean findProductById(int id);
    List<CategoryBean> findAllCategories();
    List<ProductBean> findProductsByName(String name);
    List<ProductBean> findProductsByProducerId(int id);
    OrderStats findOrderedProductsStatsByYear(int year);
    OrderStats findOrderedProductsStatsByYearAndCategory(int year);
    String getUploadPictureForm(StorageUser user);
    void updateProduct(ProductBean product);

    /**
     * Store
     */
    Store findStoreById(int id) throws FeignException;
    List<Store> findStoreByName(String name) throws FeignException;

    /**
     * Eureka
     */
    List<Application> findAllApps() throws FeignException;

    /**
     * Storage
     */
    Storage findLatestFileUploadedByOwnerId(int id);

    /**
     * Stock
     */
    List<ProductBean> findStockById(int id);
    List<ProductBean> findStockByProductId(int id);
    List<ProductBean> findStockByProductName(String name);
    void deleteStockById(int id);
    StockBean updateStock(StockBean stock);
}
