package com.foxminded.andreimarkov.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.andreimarkov.warehouse.dto.CatalogDTO;
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

@WebMvcTest(CatalogController.class)
class CatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CatalogServiceImpl catalogService;
    @MockBean
    private ProductServiceImpl productService;

    @Test
    void listCatalogs_whenGetAllCatalogs_thenShouldReturnModel() throws Exception {

        CatalogDTO catalogDTO = new CatalogDTO();
        catalogDTO.setId(10000L);
        catalogDTO.setName("Lamps");

        CatalogDTO catalogDTO2 = new CatalogDTO();
        catalogDTO2.setId(10001L);
        catalogDTO2.setName("Candlesticks");

        List<CatalogDTO> all = Arrays.asList(catalogDTO, catalogDTO2);

        when(catalogService.findAll()).thenReturn(all);
        mockMvc.perform(get("/catalogs/get-all"))
                .andExpect(model().attribute("catalogs", all));
    }

    @Test
    void showCatalogsById_whenGivenId_thenReturnThisCatalog() throws Exception {
        CatalogDTO catalogDTO = new CatalogDTO();
        catalogDTO.setId(1L);
        Optional<CatalogDTO> catalogDTOOptional = Optional.of(catalogDTO);

        when(catalogService.getById(anyLong())).thenReturn(catalogDTOOptional);

        mockMvc.perform(get("/catalogs/view/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("catalogs/view"))
                .andExpect(model().attributeExists("catalog"));
    }

    @Test
    void pageCatalogs_whenGoToIndexPage_thenShouldStatusOk() throws Exception {
        mockMvc.perform((get("/")))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void newCatalog_whenWantToAddCatalog_thenShouldGiveFormAddPage() throws Exception {
        mockMvc.perform((get("/catalogs/add-catalog")))
                .andExpect(status().isOk())
                .andExpect(view().name("catalogs/add-catalog"))
                .andExpect(model().attributeExists("catalog"));
    }

    @Test
    void addCatalog_whenPostRequest_thenShouldCreateNewCatalog() throws Exception {
        CatalogDTO catalogDTO = new CatalogDTO();
        catalogDTO.setId(2L);
        when(catalogService.save(any())).thenReturn(catalogDTO);
        mockMvc.perform(post("/catalogs/add-catalog")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/catalogs/index"));
    }

    @Test
    void showUpdateForm_whenGetToUpdateSomeCatalog_thenShouldGiveForm() throws Exception {
        CatalogDTO catalogDTO = new CatalogDTO();
        catalogDTO.setId(2L);
        Optional<CatalogDTO> catalogDTOOptional = Optional.of(catalogDTO);
        when(catalogService.getById(anyLong())).thenReturn(catalogDTOOptional);
        mockMvc.perform(get("/catalogs/edit/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("catalogs/update-catalog"))
                .andExpect(model().attributeExists("catalogDTO"));
    }

    @Test
    void updateCatalog_whenPostRequestToUpdate_thenShouldUpdateThisCatalog() throws Exception {
        CatalogDTO catalogDTO = new CatalogDTO();
        catalogDTO.setId(2L);
        when(catalogService.update(any())).thenReturn(catalogDTO);
        mockMvc.perform(post("/catalogs/update-catalog/2")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/catalogs/index"));
    }

    @Test
    void deleteCatalogById_whenGetId_thenShouldDeleteCatalogWithThisId() throws Exception {
        mockMvc.perform(get("/catalogs/delete/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/catalogs/index"));

        Mockito.verify(catalogService, times(1)).delete(anyLong());
    }
}