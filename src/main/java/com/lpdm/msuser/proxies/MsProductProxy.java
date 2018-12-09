package com.lpdm.msuser.proxies;

import com.lpdm.msuser.msproduct.ProductBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(name = "ms-product", url = "localhost:28085")
public interface MsProductProxy {

    @GetMapping(value = "/products")
    List<ProductBean> listProduct();

    @GetMapping(value="/products/{id}")
    ProductBean findProduct(@PathVariable("id") int id);

    @PostMapping(value = "/products")
    void addProduct(@RequestBody ProductBean product);

    @DeleteMapping(value="/products/{id}")
    void deleteStock(@PathVariable("id") int id);

    @PutMapping(value="/products")
    void updateStock(@RequestBody ProductBean product);
}
