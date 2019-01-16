package com.lpdm.msuser.controllers.admin;

import com.lpdm.msuser.model.admin.SearchForm;
import com.lpdm.msuser.services.admin.AdminService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/admin/orders")
public class OrderAdminController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final AdminService adminService;

    @Autowired
    public OrderAdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = {"", "/"})
    public ModelAndView adminOrders(){
        log.info("Admin order");
        return new ModelAndView("admin/fragments/orders")
                .addObject("pageTitle","Admin orders");
    }

    @GetMapping(value = {"/search", "/search/"})
    public ModelAndView searchOrder(){
        return new ModelAndView("/admin/fragments/orders")
                .addObject("pageTitle", "Search order")
                .addObject("content", "searchPage")
                .addObject("searchForm", new SearchForm())
                .addObject("payments", adminService.findAllPayment());
    }

    @PostMapping(value = {"/search", "/search/"})
    public ModelAndView searchOrderResult(
            @Valid @ModelAttribute("searchForm") SearchForm searchForm){

        log.info("SearchForm = " + searchForm);
        log.info("Keyword = " + searchForm.getKeyword());
        log.info("Value = " + searchForm.getSearchValue());

        String keyword = searchForm.getKeyword();
        Object result = null;
        try{
            switch (searchForm.getSearchValue()){
                case 1:
                    if(Pattern.compile("^\\d+$").matcher(keyword).matches())
                        result = adminService.findOrderById(Integer.valueOf(keyword));
                    else result = 500;
                    break;
                case 2:
                    result = adminService.findOrderByInvoiceReference(keyword);
                    break;
                case 3:
                    result = adminService.findAllOrdersByUserEmail(keyword);
                    break;
                case 4:
                    result = adminService.findAllOrdersByUserLastName(keyword);
            }
        }
        catch (FeignException e ){ result = e.status(); }

        return new ModelAndView("/admin/fragments/orders")
                .addObject("pageTitle", "Search order")
                .addObject("content", "searchPage")
                .addObject("result", result);
    }

    @GetMapping(value = {"/payments", "/payments/"})
    public ModelAndView getAllPayments(){

        return new ModelAndView("/admin/fragments/orders")
                .addObject("pageTitle", "Search order")
                .addObject("content", "paymentsPage")
                .addObject("result", adminService.findAllPayment());
    }
}
