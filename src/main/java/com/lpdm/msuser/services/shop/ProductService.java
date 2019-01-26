package com.lpdm.msuser.services.shop;

import com.lpdm.msuser.msproduct.ProductBean;

import java.util.List;

public interface ProductService {

    List<ProductBean> findAllProducts();
}
