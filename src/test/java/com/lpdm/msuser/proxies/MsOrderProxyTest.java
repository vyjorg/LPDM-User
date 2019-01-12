package com.lpdm.msuser.proxies;

import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.msorder.OrderBean;
import com.lpdm.msuser.msorder.OrderedProductBean;
import com.lpdm.msuser.msproduct.ProductBean;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MsOrderProxyTest {

    @Autowired
    private MsUserProxy userProxy;

    @Autowired
    MsOrderProxy orderProxy;

    @Autowired
    private MsProductProxy msProductProxy;

  // @Test
  // public void saveOrderTest(){
  //     OrderBean order = new OrderBean();
  //     AppUserBean user =  userProxy.getUserById(2);
  //     ProductBean product1 = msProductProxy.findProduct(1);
  //     ProductBean product2 = msProductProxy.findProduct(2);
  //     ProductBean product3 = msProductProxy.findProduct(3);

  //     List<OrderedProductBean> products = new ArrayList<>();
  //     OrderedProductBean p1 = new OrderedProductBean();
  //     OrderedProductBean p2 = new OrderedProductBean();
  //     OrderedProductBean p3 = new OrderedProductBean();
  //     p1.setQuantity(1);
  //     p1.setProduct(product1);
  //     p2.setQuantity(2);
  //     p2.setProduct(product2);
  //     p3.setQuantity(3);
  //     p3.setProduct(product3);
  //     products.add(p1);
  //     products.add(p2);
  //     products.add(p3);

  //     order.setId(-1);
  //     order.setCustomerId(user.getId());
  //     order.setOrderedProducts(products);

  //     OrderBean actualOrder = orderProxy.saveOrder(order);

  //     Assert.assertEquals(actualOrder,order);

  // }
}