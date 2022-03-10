package com.foxminded.andreimarkov.warehouse.controller;

import com.foxminded.andreimarkov.warehouse.dto.CatalogDTO;
import com.foxminded.andreimarkov.warehouse.exceptions.ServiceException;
import com.foxminded.andreimarkov.warehouse.service.impl.CatalogServiceImpl;
import com.foxminded.andreimarkov.warehouse.service.impl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/catalogs")
@Controller
@Slf4j
public class CatalogController {

    private final CatalogServiceImpl catalogService;
    private final ProductServiceImpl productService;

    @Autowired
    public CatalogController(CatalogServiceImpl catalogService, ProductServiceImpl productService) {
        this.catalogService = catalogService;
        this.productService = productService;
    }

    @GetMapping({"", "/index", "/index.html"})
    public String pageCatalogs() {
        return "catalogs/index";
    }

    @GetMapping({"/get-all", "/get-all.html"})
    public String listCatalogs(Model model) {
        log.debug("start get-all catalogs");
        try {
            model.addAttribute("catalogs", catalogService.findAll());
        } catch (ServiceException e) {
            log.info("no one catalog in database");
        }
        log.info("start catalogService.findAll");
        return "catalogs/get-all";
    }

    @GetMapping("/add-catalog")
    public String newCatalog(@ModelAttribute("catalog") CatalogDTO catalog) {
        return "catalogs/add-catalog";
    }

    @PostMapping("/add-catalog")
    public String addCatalog(CatalogDTO catalog, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("error in post mapping add-catalog: " + result.toString());
            return "catalogs/add-catalog";
        }
        log.debug("no error in post mapping add-catalog");
        catalogService.save(catalog);
        log.info("performed the method catalogService.save");
        return "redirect:/catalogs/index";
    }

    @GetMapping("/view/{id}")
    public String showCatalogById(@PathVariable String id, Model model) {
        log.debug("Getting view for catalog id: " + id);
        model.addAttribute("catalog", catalogService.getById(Long.valueOf(id)));
        try {
            model.addAttribute("products", productService.getProductsByCatalogId(Integer.parseInt(id)));
        } catch (ServiceException e) {
            log.info("no one product in this catalog");
        }
        log.info("add to model catalog by id: " + id);
        return "catalogs/view";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        log.debug("start to edit catalogDTO");
        CatalogDTO catalogDTO = catalogService.getById(id).get();
        log.info("edit catalogDTO with id: " + catalogDTO.getId() + " name: " + catalogDTO.getName());
        model.addAttribute("catalogDTO", catalogDTO);
        return "catalogs/update-catalog";
    }

    @PostMapping("/update-catalog/{id}")
    public String updateCatalog(@PathVariable("id") long id, @ModelAttribute("catalog") CatalogDTO catalogDTO,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("error in post mapping update-catalog: " + result.toString());
            return "catalogs/update-catalog";
        }
        log.debug("no error in post mapping update-catalog with id: " + id);
        catalogDTO.setId(id);
        catalogService.update(catalogDTO);
        log.info("performed the method catalogService.update catalog with id: " + id);
        return "redirect:/catalogs/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteCatalogById(@PathVariable("id") long id, @ModelAttribute("catalog") CatalogDTO catalogDTO) {
        log.debug("start deleting company");
        catalogService.delete(id);
        log.info("complete to delete catalog with id: " + id);
        return "redirect:/catalogs/index";
    }
}
