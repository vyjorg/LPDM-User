package com.lpdm.msuser.proxies;

import com.lpdm.msuser.msproduct.ProductBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
@FeignClient(name = "ms-product", url = "localhost:28085")
public interface MsProductProxy {

    @GetMapping(value = "/products")
    public List<ProductBean> listProduct();

    @GetMapping(value="/products/{id}")
    public ProductBean findProduct(@PathVariable("id") int id);
}
