package com.lpdm.msuser.msorder;

import com.lpdm.msuser.msproduct.ProductBean;


public class OrderedProductBean {

    private OrderedProductPKBean orderedProductPK = new OrderedProductPKBean();

    private OrderBean order;

    private ProductBean product;

    private int quantity;

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public OrderedProductPKBean getOrderedProductPK() {
        return orderedProductPK;
    }

    public void setOrderedProductPK(OrderedProductPKBean orderedProductPK) {
        this.orderedProductPK = orderedProductPK;
    }
}