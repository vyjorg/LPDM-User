package com.lpdm.msuser.controllers.admin;

import com.lpdm.msuser.model.admin.OrderStats;
import com.lpdm.msuser.model.admin.SearchDates;
import com.lpdm.msuser.model.admin.SearchForm;
import com.lpdm.msuser.msorder.enumeration.StatusEnum;
import com.lpdm.msuser.services.admin.AdminService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

        LocalDate date = LocalDate.now();
        OrderStats currentYear = adminService.findOrderStatsByYear(date.getYear());
        OrderStats lastYear = adminService.findOrderStatsByYear(date.getYear() - 1);
        OrderStats average = adminService.getAverageStats(currentYear, lastYear);

        return new ModelAndView("admin/fragments/orders")
                .addObject("pageTitle","Admin orders")
                .addObject("statsCurrentYear", currentYear)
                .addObject("statsLastYear", lastYear)
                .addObject("statsAverageYear", average);
    }

    @GetMapping(value = {"/search", "/search/"})
    public ModelAndView searchOrder(){
        return new ModelAndView("/admin/fragments/orders")
                .addObject("pageTitle", "Search order")
                .addObject("content", "searchPage")
                .addObject("searchForm", new SearchForm())
                .addObject("payments", adminService.findAllPayment())
                .addObject("selectedTab", "order_id")
                .addObject("statusList", StatusEnum.values());
    }

    @PostMapping(value = {"/search", "/search/"})
    public ModelAndView searchOrderResult(
            @Valid @ModelAttribute("searchForm") SearchForm searchForm){

        log.info("SearchForm = " + searchForm);
        log.info("Keyword = " + searchForm.getKeyword());
        log.info("Value = " + searchForm.getSearchValue());

        String keyword = searchForm.getKeyword();
        String selectedTab = null;
        Object result = null;
        try{
            switch (searchForm.getSearchValue()){
                // Search by order id
                case 1:
                    if(Pattern.compile("^\\d+$").matcher(keyword).matches())
                        result = adminService.findOrderById(Integer.valueOf(keyword));
                    else result = 500;
                    selectedTab = "order_id";
                    break;
                // Search by user id
                case 2:
                    if(Pattern.compile("^\\d+$").matcher(keyword).matches())
                        result = adminService.findAllOrdersByUserId(Integer.valueOf(keyword));
                    else result = 500;
                    selectedTab = "customer";
                    break;
                // Search by user email
                case 3:
                    result = adminService.findAllOrdersByUserEmail(keyword);
                    log.info(result.toString());
                    selectedTab = "customer";
                    break;
                // Search bu user lastname
                case 4:
                    selectedTab = "customer";
                    break;
                // Search by invoice ref
                case 5:
                    result = adminService.findOrderByInvoiceReference(keyword);
                    selectedTab = "invoice";
                    break;
                // Search by date
                case 6:
                    String date1 = keyword.substring(0, keyword.lastIndexOf(":"));
                    String date2 = keyword.substring(keyword.lastIndexOf(":") + 1);
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    SearchDates dates = new SearchDates(
                            LocalDate.parse(date1, df),
                            LocalDate.parse(date2, df));
                    result = adminService.findAllOrdersBetweenTwoDates(dates);
                    selectedTab = "date";
                    break;
            }
        }
        catch (FeignException e ){
            log.warn(e.getMessage());
            result = e.status();
        }

        return new ModelAndView("/admin/fragments/orders")
                .addObject("pageTitle", "Search order")
                .addObject("content", "searchPage")
                .addObject("result", result)
                .addObject("searchForm", new SearchForm())
                .addObject("payments", adminService.findAllPayment())
                .addObject("selectedTab", selectedTab)
                .addObject("statusList", StatusEnum.values());
    }

    @GetMapping(value = {"/payments", "/payments/"})
    public ModelAndView getAllPayments(){

        return new ModelAndView("/admin/fragments/orders")
                .addObject("pageTitle", "Search order")
                .addObject("content", "paymentsPage")
                .addObject("result", adminService.findAllPayment());
    }
}
