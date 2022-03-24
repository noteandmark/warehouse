package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcLocationDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.LocationDTO;
import com.foxminded.andreimarkov.warehouse.model.Location;
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
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class LocationServiceImplTest {

    @Mock
    private JdbcLocationDAOImpl locationDAO;
    private LocationDTO locationDTO;
    private Location location;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private LocationServiceImpl locationService;

    @BeforeEach
    void setUp() {
        locationDTO = new LocationDTO();
        location = new Location();
        when(mapper.map(locationDTO, Location.class)).thenReturn(location);
        when(mapper.map(location, LocationDTO.class)).thenReturn(locationDTO);
        locationDTO.setId(20000L);
        locationDTO.setWarehouseName("Main warehouse");
    }

    @Test
    void save() {
        when(locationDAO.save(any())).thenReturn(new Location());
        locationService.save(locationDTO);
        verify(locationDAO, only()).save(any(Location.class));
    }

    @Test
    void findAll() {
        List<Location> all = new ArrayList<>();
        all.add(location);
        when(locationDAO.findAll()).thenReturn(all);
        locationService.findAll();
        verify(locationDAO, only()).findAll();
    }

    @Test
    void getById() {
        location.setId(10000L);
        location.setWarehouseName("Main warehouse");
        when(locationDAO.getById(anyLong())).thenReturn(Optional.ofNullable(location));
        Optional<LocationDTO> actual = locationService.getById(10000L);
        assertEquals(locationDTO, actual.get());
    }

    @Test
    void update() {
        locationDTO.setWarehouseName("Updated warehouse");
        locationService.update(locationDTO);
        verify(locationDAO, times(1)).update(location);
    }

    @Test
    void delete() {
        location.setId(20000L);
        when(locationDAO.getById(anyLong())).thenReturn(Optional.of(location));
        locationService.delete(locationDTO.getId());
        verify(locationDAO, times(1)).delete(location.getId());
    }
}