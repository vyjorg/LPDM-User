package com.lpdm.msuser.controllers.admin;

import com.lpdm.msuser.model.admin.SearchForm;
import com.lpdm.msuser.services.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/admin/auth")
public class AuthAdminController {

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
    public ModelAndView searchOrder(){
        return new ModelAndView("/admin/fragments/users")
                .addObject("pageTitle", "Search user")
                .addObject("content", "searchPage")
                .addObject("searchForm", new SearchForm());
    }
}
