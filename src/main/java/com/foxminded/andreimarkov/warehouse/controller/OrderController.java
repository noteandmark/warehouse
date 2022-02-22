package com.foxminded.andreimarkov.warehouse.controller;

import com.foxminded.andreimarkov.warehouse.dto.OrderDTO;
import com.foxminded.andreimarkov.warehouse.exceptions.ServiceException;
import com.foxminded.andreimarkov.warehouse.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/orders")
@Controller
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping({"", "/index", "/index.html"})
    public String pageOrders() {
        return "orders/index";
    }

    @GetMapping({"/get-all", "/get-all.html"})
    public String listOrders(Model model) {
        log.debug("start get-all orders");
        try {
            model.addAttribute("orders", orderService.findAll());
        } catch (ServiceException e) {
            log.info("no one order in database");
        }
        log.info("start orderService.findAll");
        return "orders/get-all";
    }

    @GetMapping("/add-order")
    public String newOrder(@ModelAttribute("order") OrderDTO order) {
        return "orders/add-order";
    }

    @PostMapping("/add-order")
    public String addOrder(OrderDTO order, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("error in post mapping add-order: " + result.toString());
            return "orders/add-order";
        }
        log.debug("no error in post mapping add-order");
        orderService.save(order);
        log.info("performed the method orderService.save");
        return "redirect:/orders/index";
    }

    @GetMapping("/view/{id}")
    public String showOrderById(@PathVariable String id, Model model) {
        log.debug("Getting view for order id: " + id);
        model.addAttribute("order", orderService.getById(Long.valueOf(id)));
        log.info("add to model order by id: " + id);
        return "orders/view";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        log.debug("start to edit orderDTO");
        OrderDTO orderDTO = orderService.getById(id).get();
        log.info("edit orderDTO with id: " + orderDTO.getId() + " date: " + orderDTO.getDate());
        model.addAttribute("orderDTO", orderDTO);
        return "orders/update-order";
    }

    @PostMapping("/update-order/{id}")
    public String updateOrder(@PathVariable("id") long id, @ModelAttribute("order") OrderDTO orderDTO,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("error in post mapping update-order: " + result.toString());
            return "orders/update-order";
        }
        log.debug("no error in post mapping update-company with id: " + id);
        orderDTO.setId(id);
        orderService.update(orderDTO);
        log.info("performed the method orderService.update order with id: " + id);
        return "redirect:/orders/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrderById(@PathVariable("id") long id, @ModelAttribute("order") OrderDTO orderDTO) {
        log.debug("start deleting order");
        orderService.delete(id);
        log.info("complete to delete order with id: " + id);
        return "redirect:/orders/index";
    }
}
