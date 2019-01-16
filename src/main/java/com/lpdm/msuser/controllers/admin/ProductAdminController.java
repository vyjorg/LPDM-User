package com.lpdm.msuser.controllers.admin;

import com.lpdm.msuser.model.admin.SearchForm;
import com.lpdm.msuser.services.admin.AdminService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/admin/products")
public class ProductAdminController {

    private final AdminService adminService;

    @Autowired
    public ProductAdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = {"", "/"})
    public ModelAndView adminProducts(){

        return new ModelAndView("/admin/fragments/products")
                .addObject("pageTitle", "Admin products");
    }

    @GetMapping(value = {"/search", "/search/"})
    public ModelAndView searchProduct(){
        return new ModelAndView("/admin/fragments/products")
                .addObject("pageTitle", "Search product")
                .addObject("content", "searchPage")
                .addObject("searchForm", new SearchForm());
    }

    @PostMapping(value = {"/search", "/search/"})
    public ModelAndView searchProductResult(
            @Valid @ModelAttribute("searchForm") SearchForm searchForm){

        String keyword = searchForm.getKeyword();
        Object result = null;
        try{
            switch (searchForm.getSearchValue()){
                case 1:
                    if(Pattern.compile("^\\d+$").matcher(keyword).matches())
                        result = adminService.findProductById(Integer.valueOf(keyword));
                    else result = 500;
                    break;
                    /*
                case 2:
                    result = adminService.findOrderByInvoiceReference(keyword);
                    break;
                case 3:
                    result = adminService.findAllOrdersByUserEmail(keyword);
                    break;
                case 4:
                    result = adminService.findAllOrdersByUserLastName(keyword);
                    */
            }
        }
        catch (FeignException e ){ result = e.status(); }

        return new ModelAndView("/admin/fragments/products")
                .addObject("pageTitle", "Search product")
                .addObject("content", "searchPage")
                .addObject("result", result)
                .addObject("categories", adminService.findAllCategories());
    }
}