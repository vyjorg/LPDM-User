package com.lpdm.msuser.msorder;

import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.msorder.enumeration.StatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public class OrderBean {

    private int id;

    private double total;

    private LocalDateTime orderDate;

    private StatusEnum status;

    private PaymentBean payment;

    private int storeId;

    private StoreBean store;

    private int customerId;

    private AppUserBean customer;

    private Coupon coupon;

    private Delivery delivery;

    private double shippingCost;

    private List<OrderedProductBean> orderedProducts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public PaymentBean getPayment() {
        return payment;
    }

    public void setPayment(PaymentBean payment) {
        this.payment = payment;
    }

    public StoreBean getStore() {
        return store;
    }

    public void setStore(StoreBean store) {
        this.store = store;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public AppUserBean getCustomer() {
        return customer;
    }

    public void setCustomer(AppUserBean customer) {
        this.customer = customer;
    }

    public List<OrderedProductBean> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<OrderedProductBean> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "id=" + id +
                ", total=" + total +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", payment=" + payment +
                ", storeId=" + storeId +
                ", store=" + store +
                ", customerId=" + customerId +
                ", customer=" + customer +
                ", orderedProducts=" + orderedProducts +
                ", coupon=" + coupon +
                '}';
    }
}
