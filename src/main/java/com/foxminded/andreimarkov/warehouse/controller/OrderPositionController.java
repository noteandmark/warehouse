package com.foxminded.andreimarkov.warehouse.controller;

import com.foxminded.andreimarkov.warehouse.dto.OrderPositionDTO;
import com.foxminded.andreimarkov.warehouse.exceptions.ServiceException;
import com.foxminded.andreimarkov.warehouse.service.impl.OrderPositionServiceImpl;
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
    private final OrderPositionServiceImpl orderPositionService;

    @Autowired
    public OrderPositionController(OrderPositionServiceImpl orderPositionService) {
        this.orderPositionService = orderPositionService;
    }

    @GetMapping({"", "/index", "/index.html"})
    public String pageOrderPositions() {
        return "orderpositions/index";
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
        return "orderpositions/get-all";
    }

    @GetMapping("/add-position")
    public String newOrderPosition(@ModelAttribute("orderposition") OrderPositionDTO orderPositionDTO) {
        return "orderpositions/add-position";
    }

    @PostMapping("/add-position")
    public String addOrderPosition(OrderPositionDTO orderPositionDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("error in post mapping add-position: " + result.toString());
            return "orderpositions/add-position";
        }
        log.debug("no error in post mapping add-position");
        orderPositionService.save(orderPositionDTO);
        log.info("performed the method orderPositionService.save");
        return "redirect:/orderpositions/index";
    }

    @GetMapping("/view/{id}")
    public String showOrderPositionById(@PathVariable String id, Model model) {
        log.debug("Getting view for orderposition id: " + id);
        model.addAttribute("orderposition", orderPositionService.getById(Long.valueOf(id)));
        log.info("add to model orderposition by id: " + id);
        return "orderpositions/view";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        log.debug("start to edit orderpositionDTO");
        OrderPositionDTO orderPositionDTO = orderPositionService.getById(id).get();
        log.info("edit orderPositionDTO with id: " + orderPositionDTO.getId() + " amount: " + orderPositionDTO.getAmount());
        model.addAttribute("orderPositionDTO", orderPositionDTO);
        return "orderpositions/update-position";
    }

    @PostMapping("/update-position/{id}")
    public String updateOrderPosition(@PathVariable("id") long id, @ModelAttribute("orderposition") OrderPositionDTO orderPositionDTO,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("error in post mapping update-position: " + result.toString());
            return "orderpositions/update-position";
        }
        log.debug("no error in post mapping update-position with id: " + id);
        orderPositionDTO.setId(id);
        orderPositionService.update(orderPositionDTO);
        log.info("performed the method orderPositionService.update orderposition with id: " + id);
        return "redirect:/orderpositions/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrderPositionById(@PathVariable("id") long id, @ModelAttribute("orderposition") OrderPositionDTO orderPositionDTO) {
        log.debug("start deleting order");
        orderPositionService.delete(id);
        log.info("complete to delete orderposition with id: " + id);
        return "redirect:/orderpositions/index";
    }
}
