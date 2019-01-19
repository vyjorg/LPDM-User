package com.lpdm.msuser.proxies;

import com.lpdm.msuser.model.admin.OrderStats;
import com.lpdm.msuser.model.admin.SearchDates;
import com.lpdm.msuser.msorder.OrderBean;
import com.lpdm.msuser.msorder.PaymentBean;
import feign.FeignException;
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
import java.util.Optional;

@Component
@FeignClient(name = "zuul-server", url = "https://zuul.lpdm.kybox.fr")
@RibbonClient(name = "ms-order")
public interface MsOrderProxy {
    /**
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/ms-order/orders/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    OrderBean getOrderById(@PathVariable("id") int id) throws FeignException;

    @PostMapping(value = "/ms-order/orders/save", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    OrderBean saveOrder(@Valid @RequestBody OrderBean order);

    @GetMapping(value = "${lpdm.order.name}/orders/all/customer/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<OrderBean> findAllByUserId(@PathVariable("id") int id);

    @GetMapping(value = "/ms-order/orders/payments", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<PaymentBean> getPaymentList();

    @GetMapping(value = "${lpdm.order.name}/admin/orders/all/customer/email/{email}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<OrderBean> findAllByUserEmail(@PathVariable("email") String email);

    @GetMapping(value = "${lpdm.order.name}/admin/orders/all/customer/name/{name}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<OrderBean> findAllByUserLastName(@PathVariable("name") String name);

    @GetMapping(value = "${lpdm.order.name}/admin/orders/invoice/{ref}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    OrderBean findByInvoiceReference(@PathVariable("ref") String ref);

    @PostMapping(value = "${lpdm.order.name}/admin/orders/dates/between",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<OrderBean> findAllOrdersBetweenDates(@Valid @RequestBody SearchDates searchDates);

    @GetMapping(value = "${lpdm.order.name}/admin/orders/stats/year/{year}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    OrderStats findOrderStatsByYear(@PathVariable("year") Integer year);

    @GetMapping(value = "${lpdm.order.name}/admin/orderedproducts/stats/year/{year}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    OrderStats findOrderedProductsStatsByYear(@PathVariable("year") Integer year);

    @GetMapping(value = "${lpdm.order.name}/admin/orderedproducts/stats/year/{year}/category",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    OrderStats findOrderedProductsStatsByYearAndCategory(@PathVariable("year") Integer year);

}
