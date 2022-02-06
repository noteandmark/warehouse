package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcLocationDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.LocationDTO;
import com.foxminded.andreimarkov.warehouse.exceptions.ServiceException;
import com.foxminded.andreimarkov.warehouse.model.Location;
import com.foxminded.andreimarkov.warehouse.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        log.debug("start method save LocationDTO");
        Location location = mapper.map(locationDTO, Location.class);
        log.debug("saved location {}", location.getWarehouseName());
        return mapper.map(locationDAO.save(location), LocationDTO.class);
    }

    @Override
    public List<LocationDTO> findAll() {
        log.debug("getting findAll in locationService");
        List<Location> all = locationDAO.findAll();
        if (all.isEmpty()) {
            log.warn("there are no any location");
            throw new ServiceException("Location is empty");
        }
        return mapListOfEntityToDTO(all);
    }

    @Override
    public Optional<LocationDTO> getById(long id) {
        log.debug("getting location by id {}", id);
        Optional<Location> optionalLocation = locationDAO.getById(id);
        log.debug("get optional value");
        Location locationById;
        if (!optionalLocation.isPresent()) {
            log.warn("no location with id {}",id);
            throw new ServiceException("No location with this id");
        }
        locationById = optionalLocation.get();
        LocationDTO locationDTO = mapper.map(locationById, LocationDTO.class);
        log.debug("get locationDTO");
        return Optional.ofNullable(locationDTO);
    }

    @Override
    public LocationDTO update(LocationDTO locationDTO) {
        log.debug("starting update locationDTO");
        Location updated = locationDAO.update(mapper.map(locationDTO, Location.class));
        log.debug("updated locationDTO");
        return mapper.map(updated, LocationDTO.class);
    }

    @Override
    public int delete(long id) {
        log.debug("starting delete location with id {}",id);
        return locationDAO.delete(id);
    }

    private List<LocationDTO> mapListOfEntityToDTO(List<Location> all) {
        log.debug("start mapping List<Location> in List<LocationDTO>");
        return all.stream().map(location -> mapper.map(location,LocationDTO.class))
                .collect(Collectors.toList());
    }
}
