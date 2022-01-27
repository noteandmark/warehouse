package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcLocationDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.LocationDTO;
import com.foxminded.andreimarkov.warehouse.model.Location;
import com.foxminded.andreimarkov.warehouse.service.LocationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private final JdbcLocationDAOImpl locationDAO;
    private final ModelMapper mapper;

    @Autowired
    public LocationServiceImpl(JdbcLocationDAOImpl locationDAO, ModelMapper mapper) {
        this.locationDAO = locationDAO;
        this.mapper = mapper;
    }

    @Override
    public LocationDTO save(LocationDTO locationDTO) {
        Location location = mapper.map(locationDTO, Location.class);
        return mapper.map(locationDAO.save(location), LocationDTO.class);
    }

    @Override
    public List<LocationDTO> findAll() {
        List<Location> all = locationDAO.findAll();
        return mapListOfEntityToDTO(all);
    }

    @Override
    public Optional<LocationDTO> getById(long id) {
        Location locationById = locationDAO.getById(id).get();
        LocationDTO locationDTO = mapper.map(locationById, LocationDTO.class);
        return Optional.ofNullable(locationDTO);
    }

    @Override
    public LocationDTO update(LocationDTO locationDTO) {
        Location updated = locationDAO.update(mapper.map(locationDTO, Location.class));
        return mapper.map(updated, LocationDTO.class);
    }

    @Override
    public int delete(long id) {
        return locationDAO.delete(id);
    }

    private List<LocationDTO> mapListOfEntityToDTO(List<Location> all) {
        return all.stream().map(location -> mapper.map(location,LocationDTO.class))
                .collect(Collectors.toList());
    }
}
