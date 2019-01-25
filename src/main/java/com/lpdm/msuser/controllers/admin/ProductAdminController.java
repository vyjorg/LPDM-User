package com.lpdm.msuser.controllers.admin;

import com.lpdm.msuser.model.storage.Storage;
import com.lpdm.msuser.model.admin.OrderStats;
import com.lpdm.msuser.model.admin.SearchForm;
import com.lpdm.msuser.model.admin.StorageUser;
import com.lpdm.msuser.msproduct.ProductBean;
import com.lpdm.msuser.services.admin.AdminService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/admin/products")
public class ProductAdminController {

    private final String ADMIN_PRODUCT_PAGE = "/admin/fragments/products";
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final AdminService adminService;

    @Autowired
    public ProductAdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = {"", "/"})
    public ModelAndView adminProducts(){

        LocalDate date = LocalDate.now();
        OrderStats currentYear = adminService.findOrderedProductsStatsByYear(date.getYear());
        OrderStats lastYear = adminService.findOrderedProductsStatsByYear(date.getYear() - 1);
        OrderStats average = adminService.getAverageStats(currentYear, lastYear);

        OrderStats catCurrentYear = adminService.findOrderedProductsStatsByYearAndCategory(date.getYear());
        OrderStats catLastYear = adminService.findOrderedProductsStatsByYearAndCategory(date.getYear() - 1);
        OrderStats catAverage = adminService.getAverageStats(currentYear, lastYear);

        return new ModelAndView(ADMIN_PRODUCT_PAGE)
                .addObject("pageTitle", "Admin products")
                .addObject("statsCurrentYear", currentYear)
                .addObject("statsLastYear", lastYear)
                .addObject("statsAverageYear", average)
                .addObject("statsCatCurrentYear", catCurrentYear)
                .addObject("statsCatLastYear", catLastYear)
                .addObject("statsCatAverageYear", catAverage)
                .addObject("content", "stats");
    }

    @GetMapping(value = {"/search/{id}", "/search/{id}/"})
    public ModelAndView searchProductById(@PathVariable int id){
        ProductBean product = adminService.findProductById(id);
        List<ProductBean> result = new ArrayList<>();
        result.add(product);
        return new ModelAndView(ADMIN_PRODUCT_PAGE)
                .addObject("pageTitle", "Search product")
                .addObject("content", "searchPage")
                .addObject("result", result)
                .addObject("categories", adminService.findAllCategories())
                .addObject("searchForm", new SearchForm());
    }

    @GetMapping(value = {"/search", "/search/"})
    public ModelAndView searchProduct(){
        return new ModelAndView(ADMIN_PRODUCT_PAGE)
                .addObject("pageTitle", "Search product")
                .addObject("content", "searchPage")
                .addObject("searchForm", new SearchForm());
    }

    @PostMapping(value = {"/search", "/search/"})
    public ModelAndView searchProductResult(
            @Valid @ModelAttribute("searchForm") SearchForm searchForm){

        log.info("Key[" + searchForm.getSearchValue() + "] : " + searchForm.getKeyword());
        String keyword = searchForm.getKeyword();
        Object result = null;
        try{
            switch (searchForm.getSearchValue()){
                // Find product by id
                case 1:
                    if(Pattern.compile("^\\d+$").matcher(keyword).matches()) {
                        result = new ArrayList<ProductBean>();
                        ((ArrayList) result).add(adminService.findProductById(Integer.valueOf(keyword)));
                    }
                    else result = 500;
                    break;
                // Find product by name
                case 2:
                    result = adminService.findProductsByName(keyword);
                    break;
                // Find by producer id
                case 3 :
                    if(Pattern.compile("^\\d+$").matcher(keyword).matches()) {
                        result = new ArrayList<ProductBean>();
                        ((ArrayList) result).addAll(adminService.findProductsByProducerId(Integer.valueOf(keyword)));
                        if(((ArrayList) result).isEmpty()) {
                            result = 204;
                        }
                    }
                    else result = 500;
                    break;
            }
        }
        catch (FeignException e ){
            log.warn(e.getMessage());
            result = e.status();
        }

        return new ModelAndView(ADMIN_PRODUCT_PAGE)
                .addObject("pageTitle", "Search product")
                .addObject("content", "searchPage")
                .addObject("result", result)
                .addObject("categories", adminService.findAllCategories())
                .addObject("searchForm", new SearchForm());
    }

    @PostMapping(value = {"/upload", "/upload/"},
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_HTML_VALUE)
    public String getUploadForm(@RequestParam Map<String, String> data){

        log.info("user bean = " + data.get("id"));
        StorageUser user = new StorageUser();
        user.setId(Integer.parseInt(data.get("id")));
        user.setRestricted(Boolean.parseBoolean(data.get("restricted")));
        return adminService.getUploadPictureForm(user);

    }

    @GetMapping(value = {"/picture/owner/{id}", "/picture/owner/{id}/"})
    public Storage getLatestFileUploaded(@PathVariable int id){
        return adminService.findLatestFileUploadedByOwnerId(id);
    }

    @PostMapping(value = {"/update", "update/"})
    public ProductBean updateProduct(@RequestBody ProductBean productBean){

        log.info("Product update : " + productBean.toString());
        adminService.updateProduct(productBean);
        return productBean;
    }

    @GetMapping(value = {"/add", "/add/"})
    public ModelAndView addProduct(){
        return new ModelAndView(ADMIN_PRODUCT_PAGE)
                .addObject("pageTitle", "Add product")
                .addObject("content", "addProduct")
                .addObject("categories", adminService.findAllCategories())
                .addObject("product", new ProductBean());
    }

    @PostMapping(value = {"/search/producer", "/search/producer/"})
    public Object searchProducer(@Valid @ModelAttribute("searchProducer") SearchForm searchForm){

        Object result = null;
        switch (searchForm.getSearchValue()){
            // Search by id
            case 1:
                break;
            // Search by name
            case 2:
                break;
        }

        return result;
    }
}
