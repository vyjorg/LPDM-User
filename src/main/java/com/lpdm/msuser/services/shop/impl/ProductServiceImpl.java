package com.lpdm.msuser.services.shop.impl;

import com.lpdm.msuser.msproduct.ProductBean;
import com.lpdm.msuser.proxies.MsProductProxy;
import com.lpdm.msuser.services.shop.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final MsProductProxy productProxy;

    @Autowired
    public ProductServiceImpl(MsProductProxy productProxy) {
        this.productProxy = productProxy;
    }

    @Override
    public List<ProductBean> findAllProducts() {
        return productProxy.listProduct();
    }
}
