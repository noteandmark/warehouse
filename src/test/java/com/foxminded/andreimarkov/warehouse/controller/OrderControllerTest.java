package com.foxminded.andreimarkov.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.andreimarkov.warehouse.dto.OrderDTO;
import com.foxminded.andreimarkov.warehouse.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderServiceImpl orderService;

    @Test
    void listOrders_whenGetAllOrders_thenShouldReturnModel() throws Exception {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(10000L);
        orderDTO.setStatus("check");
        orderDTO.setDate("2022-02-22");

        OrderDTO orderDTO2 = new OrderDTO();
        orderDTO2.setId(10001L);
        orderDTO2.setStatus("check");
        orderDTO2.setDate("2022-02-22");

        List<OrderDTO> all = Arrays.asList(orderDTO, orderDTO2);

        when(orderService.findAll()).thenReturn(all);
        mockMvc.perform(get("/orders/get-all"))
                .andExpect(model().attribute("orders", all));
    }

    @Test
    void showOrderById_whenGivenId_thenReturnThisOrder() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        Optional<OrderDTO> optionalOrderDTO = Optional.of(orderDTO);

        when(orderService.getById(anyLong())).thenReturn(optionalOrderDTO);

        mockMvc.perform(get("/orders/view/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("orders/view"))
                .andExpect(model().attributeExists("order"));
    }

    @Test
    void pageOrders_whenGoToIndexPage_thenShouldStatusOk() throws Exception {
        mockMvc.perform((get("/")))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void newOrder_whenWantToAddOrder_thenShouldGiveFormAddPage() throws Exception {
        mockMvc.perform((get("/orders/add-order")))
                .andExpect(status().isOk())
                .andExpect(view().name("orders/add-order"))
                .andExpect(model().attributeExists("order"));
    }

    @Test
    void addOrder_whenPostRequest_thenShouldCreateNewOrder() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(2L);
        when(orderService.save(any())).thenReturn(orderDTO);
        mockMvc.perform(post("/orders/add-order")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/orders/index"));
    }

    @Test
    void showUpdateForm_whenGetToUpdateSomeOrder_thenShouldGiveForm() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(2L);
        Optional<OrderDTO> orderDTOOptional = Optional.of(orderDTO);
        when(orderService.getById(anyLong())).thenReturn(orderDTOOptional);
        mockMvc.perform(get("/orders/edit/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("orders/update-order"))
                .andExpect(model().attributeExists("orderDTO"));
    }

    @Test
    void updateOrder_whenPostRequestToUpdate_thenShouldUpdateThisOrder() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(2L);
        when(orderService.update(any())).thenReturn(orderDTO);
        mockMvc.perform(post("/orders/update-order/2")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/orders/index"));
    }

    @Test
    void deleteOrderById_whenGetId_thenShouldDeleteOrderWithThisId() throws Exception {
        mockMvc.perform(get("/orders/delete/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/orders/index"));

        Mockito.verify(orderService, times(1)).delete(anyLong());
    }

}