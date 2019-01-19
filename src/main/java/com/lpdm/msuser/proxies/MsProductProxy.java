package com.lpdm.msuser.proxies;

import com.lpdm.msuser.msproduct.CategoryBean;
import com.lpdm.msuser.msproduct.ProductBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(name = "zuul-server", url = "https://zuul.lpdm.kybox.fr")
@RibbonClient(name = "ms-product")
public interface MsProductProxy {

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<ProductBean> listProduct();

    @GetMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ProductBean findProduct(@PathVariable int id);

    @PostMapping(value = "/products", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void addProduct(@RequestBody ProductBean product);

    @DeleteMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void deleteProduct(@PathVariable int id);

    @PutMapping(value = "/products", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void updateProduct(@RequestBody ProductBean product);

    @GetMapping(value = "/products/category/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<ProductBean> listProductByCategory(@PathVariable int id);

    @PostMapping(value = "/products/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<ProductBean> listProductByCategory2(@RequestBody CategoryBean category);

    @GetMapping(value = "/products/producer/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<ProductBean> listProductByProducerId(@PathVariable int id);

    @GetMapping(value = "/products/name/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<ProductBean> listProductByName(@PathVariable String name);

    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<CategoryBean> listCategories();

    @GetMapping(value = "/categories/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    CategoryBean category(@PathVariable int id);

    @PostMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void addCategory(@RequestBody CategoryBean category);

    @DeleteMapping(value = "/categories/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void deleteCategory(@PathVariable int id);

    @PutMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void updateCategory(@RequestBody CategoryBean category);
}
