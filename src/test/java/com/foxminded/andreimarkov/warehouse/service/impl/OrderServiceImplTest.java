package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcOrderDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.OrderDTO;
import com.foxminded.andreimarkov.warehouse.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class OrderServiceImplTest {

    @Mock
    private JdbcOrderDAOImpl orderDAO;
    private OrderDTO orderDTO;
    private Order order;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderDTO = new OrderDTO();
        order = new Order();
        when(mapper.map(orderDTO, Order.class)).thenReturn(order);
        when(mapper.map(order, OrderDTO.class)).thenReturn(orderDTO);
        orderDTO.setId(20000L);
        orderDTO.setStatus("reserved");
        orderDTO.setDate("2022-01-27");
    }

    @Test
    void save() {
        when(orderDAO.save(any())).thenReturn(new Order());
        orderService.save(orderDTO);
        verify(orderDAO, only()).save(any(Order.class));
    }

    @Test
    void findAll() {
        orderService.findAll();
        verify(orderDAO, only()).findAll();
    }

    @Test
    void getById() {
        order.setId(10000L);
        order.setStatus("reserved");
        order.setDate("2022-01-27");
        when(orderDAO.getById(anyLong())).thenReturn(Optional.ofNullable(order));
        Optional<OrderDTO> actual = orderService.getById(10000L);
        assertEquals(orderDTO, actual.get());
    }

    @Test
    void update() {
        orderDTO.setStatus("in processing");
        orderService.update(orderDTO);
        verify(orderDAO, times(1)).update(order);
    }

    @Test
    void delete() {
        order.setId(20000L);
        when(orderDAO.getById(anyLong())).thenReturn(Optional.of(order));
        orderService.delete(orderDTO.getId());
        verify(orderDAO, times(1)).delete(order.getId());
    }
}