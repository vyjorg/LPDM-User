package com.lpdm.msuser.controllers.producer;

import com.lpdm.msuser.controllers.SessionController;
import com.lpdm.msuser.msauthentication.AppUserBean;
import com.lpdm.msuser.msproduct.CategoryBean;
import com.lpdm.msuser.msproduct.ProductBean;
import com.lpdm.msuser.msproduct.StockBean;
import com.lpdm.msuser.proxies.MsProductProxy;
import com.lpdm.msuser.proxies.MsStockProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class ProducerController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionController sessionController;

    @Autowired
    private MsProductProxy msProductProxy;

    @Autowired
    private MsStockProxy msStockProxy;

    @GetMapping("/producer/product")
    public String addProductForm(HttpSession session, Model model){
        log.info("Affichage du formulaire d'ajout de produit");

        AppUserBean user = (AppUserBean) session.getAttribute("user");
        List<CategoryBean> listCategory = msProductProxy.listCategories();
        model.addAttribute("user",user);
        model.addAttribute("listCategory",listCategory);
        sessionController.addSessionAttributes(session, model);
        return "shop/fragments/producer/product";
    }

    @PostMapping(value = {"/producer/product/add", "/producer/product/add/"})
    public ProductBean addNewProduct(@Valid @RequestBody ProductBean product){

        log.info("Product : " + product);
        return msProductProxy.addProduct(product);
    }


    @GetMapping("/producer/list")
    public String listProduct(HttpSession session, Model model){
        log.info("Affichage de la liste des produits du user");

        AppUserBean user = (AppUserBean) session.getAttribute("user");
        List<ProductBean> list = msProductProxy.listProductByProducerId(user.getId());

        model.addAttribute("user",user);
        model.addAttribute("list",list);


        sessionController.addSessionAttributes(session, model);
        return "shop/fragments/producer/list";
    }

    @GetMapping("/producer/product/{id}")
    public String modifProduct(HttpSession session, Model model,@PathVariable int id){
        log.info("Affichage du formulaire de modification du produit");

        log.info("id = "+id);
        AppUserBean user = (AppUserBean) session.getAttribute("user");
        ProductBean product = msProductProxy.findProduct(id);

        List<CategoryBean> listCategory = msProductProxy.listCategories();

        model.addAttribute("user",user);
        model.addAttribute("product",product);
        model.addAttribute("listCategory",listCategory);

        sessionController.addSessionAttributes(session, model);
        return "shop/fragments/producer/modif";
    }


    @PutMapping(value = {"/producer/product/modif", "/producer/product/modif/"})
    public void modifProduct(@Valid @RequestBody ProductBean product){

        log.info("Product : " + product);
        msProductProxy.updateProduct(product);
    }

    @GetMapping("/producer/product/stock")
    public String listProductForStock(HttpSession session, Model model){
        log.info("Affichage de la liste des produits du user");

        AppUserBean user = (AppUserBean) session.getAttribute("user");
        List<ProductBean> list = msProductProxy.listProductByProducerId(user.getId());

        model.addAttribute("user",user);
        model.addAttribute("list",list);

        sessionController.addSessionAttributes(session, model);
        return "shop/fragments/producer/stock";
    }


    @GetMapping("/producer/product/stock/add/{id}")
    public String addStock(HttpSession session, Model model , @PathVariable int id){
        AppUserBean user = (AppUserBean) session.getAttribute("user");
        ProductBean product = msProductProxy.findProduct(id);

        model.addAttribute("user",user);
        model.addAttribute("product",product);


        sessionController.addSessionAttributes(session, model);
        return "shop/fragments/producer/addStock";
    }


    @PostMapping(value = {"/producer/stock/add", "/producer/stock/add/"})
    public StockBean addNewStock(@Valid @RequestBody StockBean stock){

        log.info("Stock : " + stock);
        return msStockProxy.addNewStock(stock);
    }

    @GetMapping("/producer/product/stock/modif/{id}")
    public String listModifStock(HttpSession session, Model model , @PathVariable int id){
        AppUserBean user = (AppUserBean) session.getAttribute("user");
        List<StockBean> list = msProductProxy.findProduct(id).getListStock();

        if(list.size()==0){
            list=null;
        }

        model.addAttribute("user",user);
        model.addAttribute("list",list);

        sessionController.addSessionAttributes(session, model);
        return "shop/fragments/producer/listmodifstock";
    }

    @GetMapping("/producer/stock/modif/{id}")
    public String modifStock(HttpSession session, Model model , @PathVariable int id){
        AppUserBean user = (AppUserBean) session.getAttribute("user");
        StockBean stock = msStockProxy.findStockById(id);

        model.addAttribute("user",user);
        model.addAttribute("stock",stock);

        sessionController.addSessionAttributes(session, model);
        return "shop/fragments/producer/stockmodif";
    }

    @PutMapping(value = {"/producer/stock/modif", "/producer/stock/modif/"})
    public StockBean modifStock(@Valid @RequestBody StockBean stock){

        log.info("Stock : " + stock);
        return msStockProxy.updateStock(stock);
    }
}
