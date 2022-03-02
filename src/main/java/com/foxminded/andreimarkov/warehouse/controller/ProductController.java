package com.foxminded.andreimarkov.warehouse.controller;

import com.foxminded.andreimarkov.warehouse.dto.CatalogDTO;
import com.foxminded.andreimarkov.warehouse.dto.ProductDTO;
import com.foxminded.andreimarkov.warehouse.exceptions.ServiceException;
import com.foxminded.andreimarkov.warehouse.service.CatalogService;
import com.foxminded.andreimarkov.warehouse.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products")
@Controller
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final CatalogService catalogService;

    @Autowired
    public ProductController(ProductService productService, CatalogService catalogService) {
        this.productService = productService;
        this.catalogService = catalogService;
    }

    @GetMapping({"", "/index", "/index.html"})
    public String pageProducts() {
        return "products/index";
    }

    @GetMapping({"/get-all", "/get-all.html"})
    public String listProducts(Model model) {
        log.debug("start get-all products");
        try {
            model.addAttribute("products", productService.findAll());
        } catch (ServiceException e) {
            log.info("no one product in database");
        }
        log.info("start productService.findAll");
        return "products/get-all";
    }

    @GetMapping("/add-product")
    public String newProduct(@ModelAttribute("productDTO") ProductDTO productDTO, Model model) {
        model.addAttribute("catalogs", catalogService.findAll());
        return "products/add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute("productDTO") ProductDTO productDTO, BindingResult result) {
        if (result.hasErrors()) {
            log.error("error in post mapping add-product: " + result.toString());
            return "products/add-product";
        }
        log.debug("no error in post mapping add-product");
        productService.save(productDTO);
        log.info("performed the method productService.save");
        return "redirect:/products/index";
    }

    @GetMapping("/view/{id}")
    public String showProductById(@PathVariable String id, Model model) {
        log.debug("Getting view for product id: " + id);
        model.addAttribute("product", productService.getById(Long.valueOf(id)));
        log.info("add to model product by id: " + id);
        return "products/view";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        log.debug("start to edit productDTO");
        ProductDTO productDTO = productService.getById(id).get();
        log.info("edit productDTO with id: " + productDTO.getId() + " name: " + productDTO.getName());
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("categories", catalogService.findAll());
        return "products/update-product";
    }

    @PostMapping("/update-product/{id}")
    public String updateProduct(@ModelAttribute("productDTO")ProductDTO productDTO, @PathVariable("id") long id, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("error in post mapping update-product: " + result.toString());
            return "products/update-product";
        }
        log.debug("no error in post mapping update-product with id: " + id);
        model.addAttribute("catalogs", catalogService.findAll());
        productDTO.setId(id);
        productService.update(productDTO);
        log.info("performed the method productService.update product with id: " + id);
        return "redirect:/products/get-all";
    }

    @GetMapping("/delete/{id}")
    public String deleteProductById(@PathVariable("id") long id, @ModelAttribute("product") ProductDTO productDTO) {
        log.debug("start deleting product");
        productService.delete(id);
        log.info("complete to delete product with id: " + id);
        return "redirect:/products/index";
    }
}
