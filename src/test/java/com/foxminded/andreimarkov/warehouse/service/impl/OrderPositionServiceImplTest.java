package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcOrderPositionDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.OrderPositionDTO;
import com.foxminded.andreimarkov.warehouse.dto.ProductDTO;
import com.foxminded.andreimarkov.warehouse.model.OrderPosition;
import com.foxminded.andreimarkov.warehouse.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class OrderPositionServiceImplTest {

    @Mock
    private JdbcOrderPositionDAOImpl orderPositionDAO;
    private OrderPositionDTO orderPositionDTO;
    private OrderPosition orderPosition;
    private Product product;
    private ProductDTO productDTO;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private OrderPositionServiceImpl orderPositionService;

    @BeforeEach
    void setUp() {
        orderPositionDTO = new OrderPositionDTO();
        orderPosition = new OrderPosition();
        productDTO = new ProductDTO();
        product = new Product();
        when(mapper.map(orderPositionDTO, OrderPosition.class)).thenReturn(orderPosition);
        when(mapper.map(orderPosition, OrderPositionDTO.class)).thenReturn(orderPositionDTO);
        orderPositionDTO.setId(20000L);
        productDTO.setId(100L);
        productDTO.setName("some product");
        orderPositionDTO.setProductDTO(productDTO);
        orderPositionDTO.setAmount(10);
    }

    @Test
    void save() {
        when(orderPositionDAO.save(any())).thenReturn(new OrderPosition());
        orderPositionService.save(orderPositionDTO);
        verify(orderPositionDAO, only()).save(any(OrderPosition.class));
    }

    @Test
    void findAll() {
        List<OrderPosition> all = new ArrayList<>();
        all.add(orderPosition);
        when(orderPositionDAO.findAll()).thenReturn(all);
        orderPositionService.findAll();
        verify(orderPositionDAO, only()).findAll();
    }

    @Test
    void getById() {
        orderPosition.setId(10000L);
        product.setId(100L);
        product.setName("some product");
        orderPosition.setProduct(product);
        orderPosition.setAmount(20);
        when(orderPositionDAO.getById(anyLong())).thenReturn(Optional.ofNullable(orderPosition));
        Optional<OrderPositionDTO> actual = orderPositionService.getById(10000L);
        assertEquals(orderPositionDTO, actual.get());
    }

    @Test
    void update() {
        orderPositionDTO.setAmount(100);
        orderPositionService.update(orderPositionDTO);
        verify(orderPositionDAO, times(1)).update(orderPosition);
    }

    @Test
    void delete() {
        orderPosition.setId(20000L);
        when(orderPositionDAO.getById(anyLong())).thenReturn(Optional.of(orderPosition));
        orderPositionService.delete(orderPositionDTO.getId());
        verify(orderPositionDAO, times(1)).delete(orderPosition.getId());
    }
}