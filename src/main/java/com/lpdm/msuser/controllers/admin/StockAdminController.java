package com.lpdm.msuser.controllers.admin;

import com.lpdm.msuser.model.admin.SearchForm;
import com.lpdm.msuser.msproduct.StockBean;
import com.lpdm.msuser.services.admin.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class StockAdminController {

    private final AdminService adminService;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public StockAdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = {"/stocks", "/stocks/"})
    public ModelAndView adminStock(){
        return new ModelAndView("admin/fragments/stocks")
                .addObject("pageTitle", "Admin stocks");
    }

    @GetMapping(value = {"/stocks/search", "/stocks/search/"})
    public ModelAndView stockSearch(){
        return new ModelAndView("admin/fragments/stocks")
                .addObject("pageTitle", "Admin stocks")
                .addObject("content", "searchStock")
                .addObject("searchForm", new SearchForm());
    }

    @PostMapping(value = {"/stocks/search", "stocks/search/"})
    public ModelAndView stockSearchResult(@Valid @ModelAttribute("searchForm") SearchForm searchForm){

        String keyword = searchForm.getKeyword();
        int value = searchForm.getSearchValue();
        Object result = null;

        log.info("Key[" + value + "] : " + keyword);

        switch (value){
            // Search by stock id
            case 1:
                result = adminService.findStockById(Integer.parseInt(keyword));
                break;
            // Search by product id
            case 2:
                result = adminService.findStockByProductId(Integer.parseInt(keyword));
                break;
            // Search by product name
            case 3:
                result = adminService.findStockByProductName(keyword);
                break;
        }

        return new ModelAndView("admin/fragments/stocks")
                .addObject("pageTitle", "Admin stocks")
                .addObject("content", "searchStock")
                .addObject("searchForm", new SearchForm())
                .addObject("result", result);
    }

    @GetMapping(value = {"/stocks/add", "/stocks/add/"})
    public ModelAndView addStock(){
        return new ModelAndView("admin/fragments/stocks")
                .addObject("pageTitle", "Admin stocks")
                .addObject("content", "addStock")
                .addObject("searchForm", new SearchForm());
    }

    @PostMapping(value = {"/stocks/delete", "/stocks/delete/"})
    public String deleteStock(@RequestParam Map<String, String> data){

        log.info("Stock id = " + data.get("stockId"));
        adminService.deleteStockById(Integer.parseInt(data.get("stockId")));

        return "ok";
    }

    @PostMapping(value = {"/stocks/update", "/stocks/update/"})
    public StockBean updateStock(@RequestBody StockBean stock){

        log.info("Stock Json = " + stock);

        return adminService.updateStock(stock);
    }

    @GetMapping(value = {"/stocks/search/product/{id}", "stocks/search/product/{id}/"})
    public ModelAndView findStockById(@PathVariable int id){

        Object result = adminService.findStockByProductId(id);

        return new ModelAndView("admin/fragments/stocks")
                .addObject("pageTitle", "Admin stocks")
                .addObject("content", "searchStock")
                .addObject("searchForm", new SearchForm())
                .addObject("result", result);
    }
}
