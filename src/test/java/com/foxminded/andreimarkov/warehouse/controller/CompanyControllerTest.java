package com.foxminded.andreimarkov.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.andreimarkov.warehouse.dto.CompanyDTO;
import com.foxminded.andreimarkov.warehouse.service.impl.CompanyServiceImpl;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(CompanyController.class)
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CompanyServiceImpl companyService;

    @Test
    void listCompanies_whenGetAllCompanies_thenShouldReturnModel() throws Exception {

        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(10000L);
        companyDTO.setName("BigIndustry");
        companyDTO.setAddress("some street");
        companyDTO.setPhone("111-22-33");
        companyDTO.setBalance(100000);

        CompanyDTO companyDTO2 = new CompanyDTO();
        companyDTO2.setId(10001L);
        companyDTO2.setName("VeryBigIndustry");
        companyDTO2.setAddress("some street2");
        companyDTO2.setPhone("111-22-34");
        companyDTO2.setBalance(200000);

        List<CompanyDTO> all = Arrays.asList(companyDTO, companyDTO2);

        when(companyService.findAll()).thenReturn(all);
        mockMvc.perform(get("/companies/get-all"))
                .andExpect(model().attribute("companies", all));
    }

    @Test
    void showCompanyById_whenGivenId_thenReturnThisCompany() throws Exception {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(1L);
        Optional<CompanyDTO> companyDTOOptional = Optional.of(companyDTO);

        when(companyService.getById(anyLong())).thenReturn(companyDTOOptional);

        mockMvc.perform(get("/companies/view/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("companies/view"))
                .andExpect(model().attributeExists("company"));
    }

    @Test
    void pageCompanies_whenGoToIndexPage_thenShouldStatusOk() throws Exception {
        mockMvc.perform((get("/")))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void newCompany_whenWantToAddCompany_thenShouldGiveFormAddPage() throws Exception {
        mockMvc.perform((get("/companies/add-company")))
                .andExpect(status().isOk())
                .andExpect(view().name("companies/add-company"))
                .andExpect(model().attributeExists("company"));
    }

    @Test
    void addCompany_whenPostRequest_thenShouldCreateNewCompany() throws Exception {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(2L);
        when(companyService.save(any())).thenReturn(companyDTO);
        mockMvc.perform(post("/companies/add-company")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/companies/index"));
    }

    @Test
    void showUpdateForm_whenGetToUpdateSomeCompany_thenShouldGiveForm() throws Exception {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(2L);
        Optional<CompanyDTO> companyDTOOptional = Optional.of(companyDTO);
        when(companyService.getById(anyLong())).thenReturn(companyDTOOptional);
        mockMvc.perform(get("/companies/edit/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("companies/update-company"))
                .andExpect(model().attributeExists("companyDTO"));
    }

    @Test
    void updateCompany_whenPostRequestToUpdate_thenShouldUpdateThisCompany() throws Exception {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(2L);
        when(companyService.update(any())).thenReturn(companyDTO);
        mockMvc.perform(post("/companies/update-company/2")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/companies/index"));
    }

    @Test
    void deleteCompanyById_whenGetId_thenShouldDeleteCompanyWithThisId() throws Exception {
        mockMvc.perform(get("/companies/delete/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/companies/index"));

        Mockito.verify(companyService, times(1)).delete(anyLong());
    }
}