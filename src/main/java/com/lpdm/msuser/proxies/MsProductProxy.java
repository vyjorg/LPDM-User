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
    public List<ProductBean> listProduct();

    @GetMapping(value="/products/{id}")
    public ProductBean findProduct(@PathVariable("id") int id);

    @PostMapping(value = "/products")
    public void addProduct(@RequestBody ProductBean product);

    @DeleteMapping(value="/products/{id}")
    public void deleteStock(@PathVariable int id);

    @PutMapping(value="/products")
    public void updateStock(@RequestBody ProductBean product);
}
