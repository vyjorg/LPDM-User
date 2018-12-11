package com.lpdm.msuser.msorder;

import java.io.Serializable;
import java.util.Objects;

public class OrderedProductPKBean implements Serializable {

    protected int orderId;

    protected int productId;

    public OrderedProductPKBean() {}

    public OrderedProductPKBean(int orderId, int productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode(){
        return Objects.hash(orderId, productId);
    }

    @Override
    public boolean equals(Object object){
        if(this == object) return true;
        if(object == null || getClass() != object.getClass()) return false;
        OrderedProductPKBean that = (OrderedProductPKBean) object;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(productId, that.productId);

    }
}
