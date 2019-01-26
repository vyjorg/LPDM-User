package com.lpdm.msuser.controllers.admin;

import com.lpdm.msuser.model.admin.EurekaInstance;
import com.lpdm.msuser.services.admin.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static com.lpdm.msuser.utils.admin.ValueType.*;

@RestController
@RequestMapping(EUREKA_ADMIN_PATH)
public class EurekaAdminController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final AdminService adminService;

    @Autowired
    public EurekaAdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = DEFAULT_PATH)
    public ModelAndView adminEureka(){
        return new ModelAndView(EUREKA_FRAGMENT_PATH)
                .addObject(HTML_PAGE_TITLE, EUREKA_PAGE_TITLE);
    }

    @GetMapping(value = {"/overview", "/overview/"})
    public ModelAndView eurekaOverview(){
        return new ModelAndView(EUREKA_FRAGMENT_PATH)
                .addObject(HTML_PAGE_TITLE, EUREKA_PAGE_TITLE)
                .addObject(HTML_PAGE_CONTENT, "overview")
                .addObject(DATA, adminService.findAllApps());
    }

    @GetMapping(value = {"/unsubscribe", "/unsubscribe/"})
    public ModelAndView unsubscribeList(){
        return new ModelAndView(EUREKA_FRAGMENT_PATH)
                .addObject(HTML_PAGE_TITLE, EUREKA_PAGE_TITLE)
                .addObject(HTML_PAGE_CONTENT, "unsubscribe")
                .addObject(DATA, adminService.findAllApps());
    }

    @PostMapping(value = {"/unsubscribe", "/unsubscribe/"})
    public String unsubscribeApp(@Valid @RequestBody EurekaInstance instance){

        log.info("Unsubscribe Eureka instance: " + instance);

        adminService.deleteInstance(instance.getAppId(), instance.getInstanceId());

        return "ok";
    }
}
