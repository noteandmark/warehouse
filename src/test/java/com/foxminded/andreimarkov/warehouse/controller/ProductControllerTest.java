package com.foxminded.andreimarkov.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.andreimarkov.warehouse.dto.ProductDTO;
import com.foxminded.andreimarkov.warehouse.service.impl.CatalogServiceImpl;
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

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductServiceImpl productService;

    @MockBean
    private CatalogServiceImpl catalogService;

    @Test
    void listProducts_whenGetAllProducts_thenShouldReturnModel() throws Exception {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(10000L);
        productDTO.setCode("313003");
        productDTO.setName("Lamp bulb");
        productDTO.setDescription("some lamp");
        productDTO.setQuantity(10);
        productDTO.setPrice(25);

        ProductDTO productDTO2 = new ProductDTO();
        productDTO2.setCode("313003");
        productDTO2.setName("Lamp bulb two");
        productDTO2.setDescription("some another lamp");
        productDTO2.setQuantity(15);
        productDTO2.setPrice(55);

        List<ProductDTO> all = Arrays.asList(productDTO, productDTO2);

        when(productService.findAll()).thenReturn(all);
        mockMvc.perform(get("/products/get-all"))
                .andExpect(model().attribute("products", all));
    }

    @Test
    void showProductsById_whenGivenId_thenReturnThisProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        Optional<ProductDTO> productDTO1 = Optional.of(productDTO);

        when(productService.getById(anyLong())).thenReturn(productDTO1);

        mockMvc.perform(get("/products/view/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/view"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void pageProducts_whenGoToIndexPage_thenShouldStatusOk() throws Exception {
        mockMvc.perform((get("/")))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void newProduct_whenWantToAddProduct_thenShouldGiveFormAddPage() throws Exception {
        mockMvc.perform((get("/products/add-product")))
                .andExpect(status().isOk())
                .andExpect(view().name("products/add-product"))
                .andExpect(model().attributeExists("productDTO"));
    }

    @Test
    void addProduct_whenPostRequest_thenShouldCreateNewProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(2L);
        when(productService.save(any())).thenReturn(productDTO);
        mockMvc.perform(post("/products/add-product")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products/index"));
    }

    @Test
    void showUpdateForm_whenGetToUpdateSomeProduct_thenShouldGiveForm() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(2L);
        Optional<ProductDTO> productDTOOptional = Optional.of(productDTO);
        when(productService.getById(anyLong())).thenReturn(productDTOOptional);
        mockMvc.perform(get("/products/edit/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/update-product"))
                .andExpect(model().attributeExists("productDTO"));
    }

    @Test
    void updateProduct_whenPostRequestToUpdate_thenShouldUpdateThisProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(2L);
        when(productService.update(any())).thenReturn(productDTO);
        mockMvc.perform(post("/products/update-product/2")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products/get-all"));
    }

    @Test
    void deleteProductById_whenGetId_thenShouldDeleteProductWithThisId() throws Exception {
        mockMvc.perform(get("/products/delete/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products/index"));

        Mockito.verify(productService, times(1)).delete(anyLong());
    }
}