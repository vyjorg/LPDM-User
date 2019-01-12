package com.lpdm.msuser.proxies;

import com.lpdm.msuser.msorder.OrderBean;
import com.lpdm.msuser.msorder.PaymentBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Component
@FeignClient(name = "zuul-server", url = "https://zuul.lpdm.kybox.fr")
@RibbonClient(name = "ms-order")
public interface MsOrderProxy {
    /**
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/ms-order/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    OrderBean getOrderById(@PathVariable("id") int id);

    @PostMapping(value = "/ms-order/save", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    OrderBean saveOrder(@Valid @RequestBody OrderBean order);

    @GetMapping(value = "/ms-order/by/customer/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<OrderBean> findAllByUserId(@PathVariable("id") int id);

    @GetMapping(value = "/ms-order/payments", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<PaymentBean> getPaymentList();

}
