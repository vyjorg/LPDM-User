package com.lpdm.msuser.controllers.admin;

import com.lpdm.msuser.model.store.Store;
import com.lpdm.msuser.model.admin.SearchForm;
import com.lpdm.msuser.services.admin.AdminService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/admin")
public class StoreAdminController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final AdminService adminService;

    @Autowired
    public StoreAdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = {"/stores", "/stores/"})
    public ModelAndView adminStore(){
        return new ModelAndView("admin/fragments/stores")
                .addObject("pageTitle", "Admin stores");
    }

    @GetMapping(value = {"/stores/search", "/stores/search/"})
    public ModelAndView searchStore(){
        return new ModelAndView("/admin/fragments/stores")
                .addObject("pageTitle", "Search store")
                .addObject("content", "searchPage")
                .addObject("searchForm", new SearchForm());
    }

    @PostMapping(value = {"/stores/search", "/stores/search/"})
    public ModelAndView searchOrderResult(
            @Valid @ModelAttribute("searchForm") SearchForm searchForm){

        String keyword = searchForm.getKeyword();
        Object result = null;
        try{
            switch (searchForm.getSearchValue()){
                case 1:
                    if(Pattern.compile("^\\d+$").matcher(keyword).matches()) {
                        result = new ArrayList<Store>();
                        ((ArrayList) result).add(adminService.findStoreById(Integer.valueOf(keyword)));
                    }
                    else result = 500;
                    break;
                case 2:
                    result = adminService.findStoreByName(keyword);
                    break;
            }
        }
        catch (FeignException e ){ result = e.status(); }

        return new ModelAndView("/admin/fragments/stores")
                .addObject("pageTitle", "Search store")
                .addObject("content", "searchPage")
                .addObject("result", result);
    }

    @PostMapping(value = {"stores/update", "stores/update/"})
    public ModelAndView updateStore(@Valid @RequestBody Store store){

        return new ModelAndView("/admin/fragments/stores")
                .addObject("pageTitle", "Search store")
                .addObject("content", "searchPage")
                .addObject("result", store);
    }
}
