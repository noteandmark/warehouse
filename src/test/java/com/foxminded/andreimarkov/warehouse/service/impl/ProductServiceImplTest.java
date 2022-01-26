package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcProductDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.CatalogDTO;
import com.foxminded.andreimarkov.warehouse.dto.LocationDTO;
import com.foxminded.andreimarkov.warehouse.dto.ProductDTO;
import com.foxminded.andreimarkov.warehouse.model.Product;
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
class ProductServiceImplTest {

    @Mock
    private JdbcProductDAOImpl productDAO;
    private ProductDTO productDTO;
    private Product product;
    private CatalogDTO catalogDTO;
    private LocationDTO locationDTO;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        product = new Product();
        catalogDTO = new CatalogDTO();
        locationDTO = new LocationDTO();
        when(mapper.map(productDTO, Product.class)).thenReturn(product);
        when(mapper.map(product, ProductDTO.class)).thenReturn(productDTO);
        productDTO.setId(20000L);
        productDTO.setCode("610387");
        productDTO.setName("spotlight R50 40w");
        productDTO.setDescription("usual lamp");
        productDTO.setQuantity(100);
        productDTO.setPrice(540);
        catalogDTO.setId(100L);
        catalogDTO.setName("lamps");
        locationDTO.setId(20000L);
        locationDTO.setWarehouseName("Main warehouse");
        locationDTO.setShelfNumber(15);
        productDTO.setCatalogDTO(catalogDTO);
        productDTO.setLocationDTO(locationDTO);
    }

    @Test
    void save() {
        when(productDAO.save(any())).thenReturn(new Product());
        productService.save(productDTO);
        verify(productDAO, only()).save(any(Product.class));
    }

    @Test
    void findAll() {
        productService.findAll();
        verify(productDAO, only()).findAll();
    }

    @Test
    void getById() {
        product.setId(10000L);
        product.setCode("610387");
        product.setName("spotlight R50 40w");
        product.setDescription("usual lamp");
        product.setQuantity(100);
        product.setPrice(540);
        when(productDAO.getById(anyLong())).thenReturn(Optional.ofNullable(product));
        Optional<ProductDTO> actual = productService.getById(10000L);
        assertEquals(productDTO, actual.get());
    }

    @Test
    void update() {
        productDTO.setName("Updated product");
        productService.update(productDTO);
        verify(productDAO, times(1)).update(product);
    }

    @Test
    void delete() {
        product.setId(20000L);
        when(productDAO.getById(anyLong())).thenReturn(Optional.of(product));
        productService.delete(locationDTO.getId());
        verify(productDAO, times(1)).delete(product.getId());
    }
}