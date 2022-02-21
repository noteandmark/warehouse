package com.foxminded.andreimarkov.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.andreimarkov.warehouse.dto.PersonDTO;
import com.foxminded.andreimarkov.warehouse.service.impl.PersonServiceImpl;
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

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonServiceImpl personService;

    @Test
    void listPersons_whenGetAllPersons_thenShouldReturnModel() throws Exception {

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

        List<PersonDTO> all = Arrays.asList(personDTO, personDTO2);

        when(personService.findAll()).thenReturn(all);
        mockMvc.perform(get("/persons/get-all"))
                .andExpect(model().attribute("persons", all));
    }

    @Test
    void showPersonById_whenGivenId_thenReturnThisPerson() throws Exception {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(1L);
        Optional<PersonDTO> personDTOOptional = Optional.of(personDTO);

        when(personService.getById(anyLong())).thenReturn(personDTOOptional);

        mockMvc.perform(get("/persons/view/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("persons/view"))
                .andExpect(model().attributeExists("person"));
    }

    @Test
    void pagePersons_whenGoToIndexPage_thenShouldStatusOk() throws Exception {
        mockMvc.perform((get("/")))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void newPerson_whenWantToAddPerson_thenShouldGiveFormAddPage() throws Exception {
        mockMvc.perform((get("/persons/add-person")))
                .andExpect(status().isOk())
                .andExpect(view().name("persons/add-person"))
                .andExpect(model().attributeExists("person"));
    }

    @Test
    void addPerson_whenPostRequest_thenShouldCreateNewPerson() throws Exception {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(2L);
        when(personService.save(any())).thenReturn(personDTO);
        mockMvc.perform(post("/persons/add-person")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/persons/index"));
    }

    @Test
    void showUpdateForm_whenGetToUpdateSomePerson_thenShouldGiveForm() throws Exception {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(2L);
        Optional<PersonDTO> optionalPersonDTO = Optional.of(personDTO);
        when(personService.getById(anyLong())).thenReturn(optionalPersonDTO);
        mockMvc.perform(get("/persons/edit/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("persons/update-person"))
                .andExpect(model().attributeExists("personDTO"));
    }

    @Test
    void updatePerson_whenPostRequestToUpdate_thenShouldUpdateThisPerson() throws Exception {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(2L);
        when(personService.update(any())).thenReturn(personDTO);
        mockMvc.perform(post("/persons/update-person/2")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/persons/index"));
    }

    @Test
    void deletePersonById_whenGetId_thenShouldDeletePersonWithThisId() throws Exception {
        mockMvc.perform(get("/persons/delete/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/persons/index"));

        Mockito.verify(personService, times(1)).delete(anyLong());
    }
}