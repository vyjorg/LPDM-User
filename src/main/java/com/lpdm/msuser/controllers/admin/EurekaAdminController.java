package com.lpdm.msuser.controllers.admin;

import com.lpdm.msuser.model.admin.EurekaInstance;
import com.lpdm.msuser.services.admin.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/eureka")
public class EurekaAdminController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final AdminService adminService;

    @Autowired
    public EurekaAdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = {"", "/"})
    public ModelAndView adminEureka(){
        return new ModelAndView("admin/fragments/eureka")
                .addObject("pageTitle", "Admin eureka");
    }

    @GetMapping(value = {"/overview", "/overview/"})
    public ModelAndView eurekaOverview(){
        return new ModelAndView("admin/fragments/eureka")
                .addObject("pageTitle", "Admin eureka")
                .addObject("content", "overview")
                .addObject("data", adminService.findAllApps());
    }

    @GetMapping(value = {"/unsubscribe", "/unsubscribe/"})
    public ModelAndView unsubscribeList(){
        return new ModelAndView("admin/fragments/eureka")
                .addObject("pageTitle", "Admin eureka")
                .addObject("content", "unsubscribe")
                .addObject("data", adminService.findAllApps());
    }

    @PostMapping(value = {"/unsubscribe", "/unsubscribe/"})
    public String unsubscribeApp(@Valid @RequestBody EurekaInstance instance){

        log.info("Unsubscribe Eureka instance: " + instance);

        adminService.deleteInstance(instance.getAppId(), instance.getInstanceId());

        return "ok";
    }
}
