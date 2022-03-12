package com.foxminded.andreimarkov.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.andreimarkov.warehouse.dto.LocationDTO;
import com.foxminded.andreimarkov.warehouse.service.impl.LocationServiceImpl;
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

@WebMvcTest(LocationController.class)
class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LocationServiceImpl locationService;

    @Test
    void listLocations_whenGetAllLocations_thenShouldReturnModel() throws Exception {

        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(10000L);
        locationDTO.setWarehouseName("Main");

        LocationDTO locationDTO2 = new LocationDTO();
        locationDTO2.setId(10001L);
        locationDTO2.setWarehouseName("Second");

        List<LocationDTO> all = Arrays.asList(locationDTO, locationDTO2);

        when(locationService.findAll()).thenReturn(all);
        mockMvc.perform(get("/locations/get-all"))
                .andExpect(model().attribute("locations", all));
    }

    @Test
    void showLocationsById_whenGivenId_thenReturnThisLocation() throws Exception {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(1L);
        Optional<LocationDTO> optionalLocationDTO = Optional.of(locationDTO);

        when(locationService.getById(anyLong())).thenReturn(optionalLocationDTO);

        mockMvc.perform(get("/locations/view/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("locations/view"))
                .andExpect(model().attributeExists("location"));
    }

    @Test
    void pageLocations_whenGoToIndexPage_thenShouldStatusOk() throws Exception {
        mockMvc.perform((get("/")))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void newLocation_whenWantToAddLocation_thenShouldGiveFormAddPage() throws Exception {
        mockMvc.perform((get("/locations/add-location")))
                .andExpect(status().isOk())
                .andExpect(view().name("locations/add-location"))
                .andExpect(model().attributeExists("location"));
    }

    @Test
    void addLocation_whenPostRequest_thenShouldCreateNewLocation() throws Exception {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(2L);
        when(locationService.save(any())).thenReturn(locationDTO);
        mockMvc.perform(post("/locations/add-location")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/locations/index"));
    }

    @Test
    void showUpdateForm_whenGetToUpdateSomeLocation_thenShouldGiveForm() throws Exception {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(2L);
        Optional<LocationDTO> optionalLocationDTO = Optional.of(locationDTO);
        when(locationService.getById(anyLong())).thenReturn(optionalLocationDTO);
        mockMvc.perform(get("/locations/edit/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("locations/update-location"))
                .andExpect(model().attributeExists("locationDTO"));
    }

    @Test
    void updateLocation_whenPostRequestToUpdate_thenShouldUpdateThisLocation() throws Exception {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(2L);
        when(locationService.update(any())).thenReturn(locationDTO);
        mockMvc.perform(post("/locations/update-location/2")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/locations/index"));
    }

    @Test
    void deleteLocationById_whenGetId_thenShouldDeleteLocationWithThisId() throws Exception {
        mockMvc.perform(get("/locations/delete/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/locations/index"));

        Mockito.verify(locationService, times(1)).delete(anyLong());
    }
}