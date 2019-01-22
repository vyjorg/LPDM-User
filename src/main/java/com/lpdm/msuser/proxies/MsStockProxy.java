package com.lpdm.msuser.proxies;

import com.lpdm.msuser.msproduct.StockBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name = "${lpdm.zuul.name}", url = "${lpdm.zuul.uri}")
@RibbonClient(name = "${lpdm.stock.name}")
public interface MsStockProxy {

    @GetMapping(value = "${lpdm.stock.name}/stocks/{id}")
    StockBean findStockById(@PathVariable int id);

    @DeleteMapping(value = "${lpdm.stock.name}/stocks/{id}")
    void deleteStockById(@PathVariable int id);

    @PutMapping(value = "${lpdm.stock.name}/stocks")
    StockBean updateStock(@RequestBody StockBean stock);
}
