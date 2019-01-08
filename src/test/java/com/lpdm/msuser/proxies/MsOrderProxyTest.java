package com.lpdm.msuser.proxies;

import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.msorder.OrderBean;
import com.lpdm.msuser.msproduct.ProductBean;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MsOrderProxyTest {

    @Autowired
    private MsUserProxy userProxy;

    @Autowired
    private MsProductProxy msProductProxy;

    @Test
    public void saveOrderTest(){
        OrderBean order = new OrderBean();
        AppUserBean user =  userProxy.getUserById(2);
        ProductBean product1 = msProductProxy.findProduct(1);
        ProductBean product2 = msProductProxy.findProduct(2);
        ProductBean product3 = msProductProxy.findProduct(3);

        List<ProductBean> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);

        order.setId(-1);
        order.setCustomer(user);

    }
}