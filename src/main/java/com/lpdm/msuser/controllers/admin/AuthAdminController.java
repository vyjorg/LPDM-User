package com.lpdm.msuser.controllers.admin;

import com.lpdm.msuser.model.admin.SearchForm;
import com.lpdm.msuser.model.location.Address;
import com.lpdm.msuser.model.location.City;
import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.services.admin.AdminService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.lpdm.msuser.utils.admin.ValueType.*;

@RestController
@RequestMapping(ADMIN_AUTH_PATH)
public class AuthAdminController {

    private final String DEFAULT_PATH = "/admin/fragments/users/users";
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private final AdminService adminService;

    @Autowired
    public AuthAdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = {"", "/"})
    public ModelAndView adminAuth(){
        return new ModelAndView(DEFAULT_PATH)
                .addObject(HTML_PAGE_TITLE, "Admin users");
    }

    @GetMapping(value = {"/search", "/search/"})
    public ModelAndView searchUser(){
        return new ModelAndView(DEFAULT_PATH)
                .addObject(HTML_PAGE_TITLE, "Search user")
                .addObject(HTML_PAGE_CONTENT, "searchPage")
                .addObject(HTML_PAGE_SEARCH_FORM, new SearchForm());
    }

    @PostMapping(value = {"/search", "/search/"})
    public ModelAndView searchUserResult(
            @Valid @ModelAttribute(HTML_PAGE_SEARCH_FORM) SearchForm searchForm){

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

        return new ModelAndView(DEFAULT_PATH)
                .addObject(HTML_PAGE_TITLE, "Search user")
                .addObject(HTML_PAGE_CONTENT, "searchPage")
                .addObject(HTML_RESULT_OBJECT, result)
                .addObject(HTML_PAGE_SEARCH_FORM, new SearchForm())
                .addObject("roleList", adminService.findAllUserRoles());
    }

    @GetMapping(value = {"/add", "/add/"})
    public ModelAndView addUser(){
        return new ModelAndView(DEFAULT_PATH)
                .addObject(HTML_PAGE_TITLE, "Search user")
                .addObject(HTML_PAGE_CONTENT, "addPage")
                .addObject("roleList", adminService.findAllUserRoles());
    }

    @PostMapping(value = {"/add", "/add/"})
    public AppUserBean addNewUser(@Valid @RequestBody AppUserBean user){

        user.setRegistrationDate(LocalDateTime.now());
        log.info("User : " + user);
        return adminService.addNewUser(user);
    }

    @GetMapping(value = {"/search/address", "/search/address/"})
    public ModelAndView searchUserForAddress(){
        return new ModelAndView(DEFAULT_PATH)
                .addObject(HTML_PAGE_TITLE, "Search user")
                .addObject(HTML_PAGE_CONTENT, "searchAddressPage")
                .addObject(HTML_PAGE_SEARCH_FORM, new SearchForm());
    }

    @PostMapping(value = {"/search/address", "/search/address/"})
    public ModelAndView searchAddressResult(
            @Valid @ModelAttribute(HTML_PAGE_SEARCH_FORM) SearchForm searchForm){

        String keyword = searchForm.getKeyword();
        Object result = null;
        try{
            switch (searchForm.getSearchValue()){
                // Search by user id
                case 3:
                    log.info("Search user by ID");
                    if(Pattern.compile("^\\d+$").matcher(keyword).matches())
                        result = adminService.findUserById(Integer.parseInt(keyword));
                    else result = 500;
                    break;
                // Search by user last name
                case 4:
                    log.info("Search user by lastName");
                    result = adminService.findUserByLastName(keyword);
                    break;
            }
        }
        catch (FeignException e ){
            log.warn(e.getMessage());
            result = e.status();
        }

        List<AppUserBean> userList = new ArrayList<>((List<AppUserBean>) result);
        List<Address> addressList = null;
        if(result instanceof ArrayList) {
            addressList = new ArrayList<>();

            for(AppUserBean user : userList){

                try{
                    addressList.add(adminService.findAddressById(user.getAddressId()));
                }
                catch (FeignException e){
                    log.warn(e.getMessage());
                    addressList.add(null);
                }
            }
        }

        return new ModelAndView(DEFAULT_PATH)
                .addObject(HTML_PAGE_TITLE, "Search user")
                .addObject(HTML_PAGE_CONTENT, "searchAddressPage")
                .addObject(HTML_RESULT_OBJECT, result)
                .addObject(HTML_PAGE_SEARCH_FORM, new SearchForm())
                .addObject("addressList", addressList);
    }

    @PostMapping(value = {"/search/address/cities", "/search/address/cities/"})
    public List<City> searchAddressResult(@RequestParam Map<String, String> data){

        log.info("Search cities for zipCode " + data.get("zipCode"));
        return adminService.findCitiesByZipCode(data.get("zipCode"));
    }

    @PostMapping(value = {"/add/address", "/add/address/"})
    public Address addNewAddress(@Valid @RequestBody Address address){

        log.info("Address : " + address);
        Address addressSaved = adminService.saveNewAddress(address);
        return addressSaved;
    }
}
