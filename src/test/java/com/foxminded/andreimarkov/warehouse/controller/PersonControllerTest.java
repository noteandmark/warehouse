package com.foxminded.andreimarkov.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.andreimarkov.warehouse.dto.PersonDTO;
import com.foxminded.andreimarkov.warehouse.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonServiceImpl personService;

    @Test
    void listPersons_whenGetPersons_thenStatus200() throws Exception {

        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(10000L);
        personDTO.setFirstName("First");
        personDTO.setSurName("Last");
        personDTO.setAddress("some street");
        personDTO.setPhone("111-22-33");
        personDTO.setBalance(0);

        PersonDTO personDTO2 = new PersonDTO();
        personDTO2.setId(10001L);
        personDTO2.setFirstName("Second");
        personDTO2.setSurName("Last");
        personDTO2.setAddress("some street2");
        personDTO2.setPhone("111-22-34");
        personDTO2.setBalance(1);

        Mockito.when(personService.findAll()).thenReturn(Arrays.asList(personDTO, personDTO2));
        mockMvc.perform(
                        get("/persons"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(personDTO, personDTO2))));
    }
}