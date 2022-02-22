package com.foxminded.andreimarkov.warehouse.controller;

import com.foxminded.andreimarkov.warehouse.dto.ProductDTO;
import com.foxminded.andreimarkov.warehouse.exceptions.ServiceException;
import com.foxminded.andreimarkov.warehouse.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/products")
@Controller
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
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
    public String newProduct(@ModelAttribute("product") ProductDTO product) {
        return "products/add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(ProductDTO product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("error in post mapping add-product: " + result.toString());
            return "products/add-product";
        }
        log.debug("no error in post mapping add-product");
        productService.save(product);
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
        return "products/update-product";
    }

    @PostMapping("/update-product/{id}")
    public String updateProduct(@PathVariable("id") long id, @ModelAttribute("catalog") ProductDTO productDTO,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("error in post mapping update-product: " + result.toString());
            return "products/update-product";
        }
        log.debug("no error in post mapping update-product with id: " + id);
        productDTO.setId(id);
        productService.update(productDTO);
        log.info("performed the method productService.update product with id: " + id);
        return "redirect:/products/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteProductById(@PathVariable("id") long id, @ModelAttribute("product") ProductDTO productDTO) {
        log.debug("start deleting product");
        productService.delete(id);
        log.info("complete to delete product with id: " + id);
        return "redirect:/products/index";
    }
}
