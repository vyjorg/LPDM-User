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

    // Test OK
    @GetMapping(value = "/ms-product/products", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<ProductBean> listProduct();

    // Test OK
    @GetMapping(value = "ms-product/products/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ProductBean findProduct(@PathVariable int id);

    // Test OK
    @PostMapping(value = "ms-product/products", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ProductBean addProduct(@RequestBody ProductBean product);

    @DeleteMapping(value = "ms-product/products/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void deleteProduct(@PathVariable int id);

    // Test OK
    @PutMapping(value = "ms-product/products", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void updateProduct(@RequestBody ProductBean product);

    @GetMapping(value = "ms-product/products/category/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<ProductBean> listProductByCategory(@PathVariable int id);

    @PostMapping(value = "ms-product/products/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<ProductBean> listProductByCategory2(@RequestBody CategoryBean category);

    @GetMapping(value = "ms-product/products/producer/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<ProductBean> listProductByProducerId(@PathVariable int id);

    @GetMapping(value = "ms-product/products/name/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<ProductBean> listProductByName(@PathVariable String name);

    @GetMapping(value = "ms-product/categories", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<CategoryBean> listCategories();

    @GetMapping(value = "ms-product/categories/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    CategoryBean category(@PathVariable int id);

    @PostMapping(value = "ms-product/categories", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void addCategory(@RequestBody CategoryBean category);

    @DeleteMapping(value = "ms-product/categories/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void deleteCategory(@PathVariable int id);

    @PutMapping(value = "ms-product/categories", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void updateCategory(@RequestBody CategoryBean category);




}
