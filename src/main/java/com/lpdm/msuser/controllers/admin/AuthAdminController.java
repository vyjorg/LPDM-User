package com.lpdm.msuser.controllers.admin;

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
@RequestMapping("/admin/auth")
public class AuthAdminController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final AdminService adminService;

    @Autowired
    public AuthAdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = {"", "/"})
    public ModelAndView adminAuth(){
        return new ModelAndView("admin/fragments/users")
                .addObject("pageTitle", "Admin users");
    }

    @GetMapping(value = {"/search", "/search/"})
    public ModelAndView searchUser(){
        return new ModelAndView("/admin/fragments/users")
                .addObject("pageTitle", "Search user")
                .addObject("content", "searchPage")
                .addObject("searchForm", new SearchForm());
    }

    @PostMapping(value = {"/search", "/search/"})
    public ModelAndView searchUserResult(
            @Valid @ModelAttribute("searchForm") SearchForm searchForm){

        String keyword = searchForm.getKeyword();
        Object result = null;
        try{
            switch (searchForm.getSearchValue()){
                // Search by user id
                case 1:
                    log.info("Search user by ID");
                    if(Pattern.compile("^\\d+$").matcher(keyword).matches())
                        result = adminService.findUserById(Integer.parseInt(keyword));
                    else result = 500;
                    break;
                // Search by user last name
                case 2:
                    log.info("Search user by lastName");
                    result = adminService.findUserByLastName(keyword);
                    break;
            }
        }
        catch (FeignException e ){
            log.warn(e.getMessage());
            result = e.status();
        }

        return new ModelAndView("/admin/fragments/users")
                .addObject("pageTitle", "Search user")
                .addObject("content", "searchPage")
                .addObject("result", result)
                .addObject("searchForm", new SearchForm());
    }
}
