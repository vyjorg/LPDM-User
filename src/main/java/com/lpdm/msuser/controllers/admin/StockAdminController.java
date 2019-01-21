package com.lpdm.msuser.controllers.admin;

import com.lpdm.msuser.model.admin.SearchForm;
import com.lpdm.msuser.services.admin.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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
                break;
            // Search by product id
            case 2:
                break;
            // Search by product name
            case 3:
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
}
