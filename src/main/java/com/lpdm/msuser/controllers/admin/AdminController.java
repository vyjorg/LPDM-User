package com.lpdm.msuser.controllers.admin;

import com.lpdm.msuser.model.admin.SearchForm;
import com.lpdm.msuser.services.admin.AdminService;
import feign.FeignException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import org.slf4j.Logger;

import javax.validation.Valid;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = {"", "/", "/login", "/login/"})
    public ModelAndView adminHome(){
        log.info("URL : /admin/");
        return new ModelAndView("admin/fragments/login")
                .addObject("pageTitle", "Admin home");
    }

}
