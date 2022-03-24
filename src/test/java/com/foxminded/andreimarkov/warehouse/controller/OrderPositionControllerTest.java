package com.foxminded.andreimarkov.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.andreimarkov.warehouse.dto.OrderPositionDTO;
import com.foxminded.andreimarkov.warehouse.service.impl.OrderPositionServiceImpl;
import com.foxminded.andreimarkov.warehouse.service.impl.ProductServiceImpl;
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

@WebMvcTest(OrderPositionController.class)
class OrderPositionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderPositionServiceImpl orderPositionService;
    @MockBean
    private ProductServiceImpl productService;

    @Test
    void listOrderPositions_whenGetAllOrderPositions_thenShouldReturnModel() throws Exception {

        OrderPositionDTO orderPositionDTO = new OrderPositionDTO();
        orderPositionDTO.setId(10000L);
        orderPositionDTO.setAmount(100);
        orderPositionDTO.setProductId(1);

        OrderPositionDTO orderPositionDTO2 = new OrderPositionDTO();
        orderPositionDTO2.setId(10001L);
        orderPositionDTO2.setAmount(200);
        orderPositionDTO2.setProductId(1);

        List<OrderPositionDTO> all = Arrays.asList(orderPositionDTO, orderPositionDTO2);

        when(orderPositionService.findAll()).thenReturn(all);
        mockMvc.perform(get("/orderpositions/get-all"))
                .andExpect(model().attribute("orderpositions", all));
    }

    @Test
    void showOrderPositionById_whenGivenId_thenReturnThisOrder() throws Exception {
        OrderPositionDTO orderPositionDTO = new OrderPositionDTO();
        orderPositionDTO.setId(1L);
        Optional<OrderPositionDTO> orderPositionDTOOptional = Optional.of(orderPositionDTO);

        when(orderPositionService.getById(anyLong())).thenReturn(orderPositionDTOOptional);

        mockMvc.perform(get("/orderpositions/view/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("orderpositions/view"))
                .andExpect(model().attributeExists("orderposition"));
    }

    @Test
    void pageOrderPositions_whenGoToIndexPage_thenShouldStatusOk() throws Exception {
        mockMvc.perform((get("/")))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void newOrderPosition_whenWantToAddOrderPosition_thenShouldGiveFormAddPage() throws Exception {
        mockMvc.perform((get("/orderpositions/add-position")))
                .andExpect(status().isOk())
                .andExpect(view().name("orderpositions/add-position"))
                .andExpect(model().attributeExists("orderposition"));
    }

    @Test
    void addOrderPosition_whenPostRequest_thenShouldCreateNewOrderPosition() throws Exception {
        OrderPositionDTO orderPositionDTO = new OrderPositionDTO();
        orderPositionDTO.setId(2L);
        when(orderPositionService.save(any())).thenReturn(orderPositionDTO);
        mockMvc.perform(post("/orderpositions/add-position")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/orderpositions/index"));
    }

    @Test
    void showUpdateForm_whenGetToUpdateSomeOrder_thenShouldGiveForm() throws Exception {
        OrderPositionDTO orderPositionDTO = new OrderPositionDTO();
        orderPositionDTO.setId(2L);
        Optional<OrderPositionDTO> orderPositionDTOOptional = Optional.of(orderPositionDTO);
        when(orderPositionService.getById(anyLong())).thenReturn(orderPositionDTOOptional);
        mockMvc.perform(get("/orderpositions/edit/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("orderpositions/update-position"))
                .andExpect(model().attributeExists("orderPositionDTO"));
    }

    @Test
    void updateOrderPosition_whenPostRequestToUpdate_thenShouldUpdateThisOrderPosition() throws Exception {
        OrderPositionDTO orderPositionDTO = new OrderPositionDTO();
        orderPositionDTO.setId(2L);
        when(orderPositionService.update(any())).thenReturn(orderPositionDTO);
        mockMvc.perform(post("/orderpositions/update-position/2")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/orderpositions/index"));
    }

    @Test
    void deleteOrderPositionById_whenGetId_thenShouldDeleteOrderPositionWithThisId() throws Exception {
        mockMvc.perform(get("/orderpositions/delete/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/orderpositions/index"));

        Mockito.verify(orderPositionService, times(1)).delete(anyLong());
    }
}