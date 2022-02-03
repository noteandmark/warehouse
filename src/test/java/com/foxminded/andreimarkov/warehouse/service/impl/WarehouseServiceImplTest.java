package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcWarehouseDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.WarehouseDTO;
import com.foxminded.andreimarkov.warehouse.model.Warehouse;
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
class WarehouseServiceImplTest {

    @Mock
    private JdbcWarehouseDAOImpl warehouseDAO;
    private WarehouseDTO warehouseDTO;
    private Warehouse warehouse;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    @BeforeEach
    void setUp() {
        warehouseDTO = new WarehouseDTO();
        warehouse = new Warehouse();
        when(mapper.map(warehouseDTO, Warehouse.class)).thenReturn(warehouse);
        when(mapper.map(warehouse, WarehouseDTO.class)).thenReturn(warehouseDTO);
        warehouseDTO.setId(20000L);
        warehouseDTO.setName("Main warehouse");
    }

    @Test
    void save() {
        when(warehouseDAO.save(any())).thenReturn(new Warehouse());
        warehouseService.save(warehouseDTO);
        verify(warehouseDAO, only()).save(any(Warehouse.class));
    }

    @Test
    void findAll() {
        List<Warehouse> all = new ArrayList<>();
        all.add(warehouse);
        when(warehouseDAO.findAll()).thenReturn(all);
        warehouseService.findAll();
        verify(warehouseDAO, only()).findAll();
    }

    @Test
    void getById() {
        warehouse.setId(10000L);
        warehouse.setName("Main warehouse");
        when(warehouseDAO.getById(anyLong())).thenReturn(Optional.ofNullable(warehouse));
        Optional<WarehouseDTO> actual = warehouseService.getById(10000L);
        assertEquals(warehouseDTO, actual.get());
    }

    @Test
    void update() {
        warehouseDTO.setName("Additional warehouse");
        warehouseService.update(warehouseDTO);
        verify(warehouseDAO, times(1)).update(warehouse);
    }

    @Test
    void delete() {
        warehouse.setId(20000L);
        when(warehouseDAO.getById(anyLong())).thenReturn(Optional.of(warehouse));
        warehouseService.delete(warehouseDTO.getId());
        verify(warehouseDAO, times(1)).delete(warehouse.getId());
    }
}