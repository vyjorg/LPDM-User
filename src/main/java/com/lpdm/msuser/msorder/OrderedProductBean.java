package com.lpdm.msuser.msorder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lpdm.msuser.msproduct.ProductBean;

import javax.validation.constraints.NotNull;


public class OrderedProductBean {

    private int id;

    private OrderBean order;

    private int productId;

    private ProductBean product;

    private int quantity;

    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderedProductBean{" +
                "id=" + id +
                ", order=" + order +
                ", productId=" + productId +
                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
