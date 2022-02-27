package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcCatalogDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.CatalogDTO;
import com.foxminded.andreimarkov.warehouse.model.Catalog;
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
class CatalogServiceImplTest {

    @Mock
    private JdbcCatalogDAOImpl catalogDAO;
    private CatalogDTO catalogDTO;
    private Catalog catalog;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private CatalogServiceImpl catalogService;

    @BeforeEach
    void setUp() {
        catalogDTO = new CatalogDTO();
        catalog = new Catalog();
        when(mapper.map(catalogDTO, Catalog.class)).thenReturn(catalog);
        when(mapper.map(catalog, CatalogDTO.class)).thenReturn(catalogDTO);
        catalogDTO.setCatalogId(20000L);
        catalogDTO.setName("Lamps");
    }

    @Test
    void save() {
        when(catalogDAO.save(any())).thenReturn(new Catalog());
        catalogService.save(catalogDTO);
        verify(catalogDAO, only()).save(any(Catalog.class));
    }

    @Test
    void findAll() {
        List<Catalog> all = new ArrayList<>();
        all.add(catalog);
        when(catalogDAO.findAll()).thenReturn(all);
        catalogService.findAll();
        verify(catalogDAO, only()).findAll();
    }

    @Test
    void getById() {
        catalog.setCatalogId(10000L);
        catalog.setName("Modern Lamps");
        when(catalogDAO.getById(anyLong())).thenReturn(Optional.ofNullable(catalog));
        Optional<CatalogDTO> actual = catalogService.getById(10000L);
        assertEquals(catalogDTO, actual.get());
    }

    @Test
    void update() {
        catalogDTO.setName("Updated Lamps");
        catalogService.update(catalogDTO);
        verify(catalogDAO, times(1)).update(catalog);
    }

    @Test
    void delete() {
        catalog.setCatalogId(20000L);
        when(catalogDAO.getById(anyLong())).thenReturn(Optional.of(catalog));
        catalogService.delete(catalogDTO.getCatalogId());
        verify(catalogDAO, times(1)).delete(catalog.getCatalogId());
    }
}