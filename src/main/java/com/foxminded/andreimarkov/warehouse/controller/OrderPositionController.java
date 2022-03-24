package com.foxminded.andreimarkov.warehouse.controller;

import com.foxminded.andreimarkov.warehouse.dto.OrderPositionDTO;
import com.foxminded.andreimarkov.warehouse.exceptions.ServiceException;
import com.foxminded.andreimarkov.warehouse.service.impl.OrderPositionServiceImpl;
import com.foxminded.andreimarkov.warehouse.service.impl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/orderpositions")
@Controller
@Slf4j
public class OrderPositionController {
    public static final String REDIRECT_ORDERPOSITIONS_INDEX = "redirect:/orderpositions/index";
    public static final String ORDERPOSITIONS_UPDATE_POSITION = "orderpositions/update-position";
    public static final String ORDERPOSITIONS_VIEW = "orderpositions/view";
    public static final String ORDERPOSITIONS_ADD_POSITION = "orderpositions/add-position";
    public static final String ORDERPOSITIONS_GET_ALL = "orderpositions/get-all";
    
    private final OrderPositionServiceImpl orderPositionService;
    private final ProductServiceImpl productService;

    @Autowired
    public OrderPositionController(OrderPositionServiceImpl orderPositionService, ProductServiceImpl productService) {
        this.orderPositionService = orderPositionService;
        this.productService = productService;
    }

    @GetMapping({"", "/index", "/index.html"})
    public String pageOrderPositions() {
        return REDIRECT_ORDERPOSITIONS_INDEX;
    }

    @GetMapping({"/get-all", "/get-all.html"})
    public String listOrderPositions(Model model) {
        log.debug("start get-all orderpositions");
        try {
            model.addAttribute("orderpositions", orderPositionService.findAll());
        } catch (ServiceException e) {
            log.info("no one orderposition in database");
        }
        log.info("start orderPositionService.findAll");
        return ORDERPOSITIONS_GET_ALL;
    }

    @GetMapping("/add-position")
    public String newOrderPosition(@ModelAttribute("orderposition") OrderPositionDTO orderPositionDTO, Model model) {
        model.addAttribute("products",productService.findAll());
        return ORDERPOSITIONS_ADD_POSITION;
    }

    @PostMapping("/add-position")
    public String addOrderPosition(@ModelAttribute("orderPositionDTO") OrderPositionDTO orderPositionDTO, BindingResult result) {
        if (result.hasErrors()) {
            log.error("error in post mapping add-position: " + result.toString());
            return ORDERPOSITIONS_ADD_POSITION;
        }
        log.debug("no error in post mapping add-position");
        orderPositionService.save(orderPositionDTO);
        log.info("performed the method orderPositionService.save");
        return REDIRECT_ORDERPOSITIONS_INDEX;
    }

    @GetMapping("/view/{id}")
    public String showOrderPositionById(@PathVariable String id, Model model) {
        log.debug("Getting view for orderposition id: " + id);
        model.addAttribute("orderposition", orderPositionService.getById(Long.valueOf(id)));
        log.info("add to model orderposition by id: " + id);
        return ORDERPOSITIONS_VIEW;
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        log.debug("start to edit orderpositionDTO");
        OrderPositionDTO orderPositionDTO = orderPositionService.getById(id).get();
        log.info("edit orderPositionDTO with id: " + orderPositionDTO.getId() + " amount: " + orderPositionDTO.getAmount());
        model.addAttribute("orderPositionDTO", orderPositionDTO);
        model.addAttribute("products",productService.findAll());
        return ORDERPOSITIONS_UPDATE_POSITION;
    }

    @PostMapping("/update-position/{id}")
    public String updateOrderPosition(@PathVariable("id") long id, @ModelAttribute("orderposition") OrderPositionDTO orderPositionDTO,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("error in post mapping update-position: " + result.toString());
            return ORDERPOSITIONS_UPDATE_POSITION;
        }
        log.debug("no error in post mapping update-position with id: " + id);
        orderPositionDTO.setId(id);
        orderPositionService.update(orderPositionDTO);
        log.info("performed the method orderPositionService.update orderposition with id: " + id);
        return REDIRECT_ORDERPOSITIONS_INDEX;
    }

    @GetMapping("/delete/{id}")
    public String deleteOrderPositionById(@PathVariable("id") long id, @ModelAttribute("orderposition") OrderPositionDTO orderPositionDTO) {
        log.debug("start deleting order");
        orderPositionService.delete(id);
        log.info("complete to delete orderposition with id: " + id);
        return REDIRECT_ORDERPOSITIONS_INDEX;
    }
}
