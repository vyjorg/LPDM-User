package com.lpdm.msuser.proxies;

import com.lpdm.msuser.msorder.OrderBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "ms-order", url = "localhost:28083")
public interface MsOrderProxy {

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    OrderBean getOrderById(@PathVariable int id);
}
