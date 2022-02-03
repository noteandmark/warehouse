package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcWarehouseDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.WarehouseDTO;
import com.foxminded.andreimarkov.warehouse.exceptions.ServiceException;
import com.foxminded.andreimarkov.warehouse.model.Warehouse;
import com.foxminded.andreimarkov.warehouse.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WarehouseServiceImpl implements WarehouseService {

    private final JdbcWarehouseDAOImpl warehouseDAO;
    private final ModelMapper mapper;

    @Autowired
    public WarehouseServiceImpl(JdbcWarehouseDAOImpl warehouseDAO, ModelMapper mapper) {
        this.warehouseDAO = warehouseDAO;
        this.mapper = mapper;
    }

    @Override
    public WarehouseDTO save(WarehouseDTO warehouseDTO) {
        log.debug("start method save warehouseDTO");
        Warehouse warehouse = mapper.map(warehouseDTO, Warehouse.class);
        log.debug("saved warehouse id {}", warehouse.getId());
        return mapper.map(warehouseDAO.save(warehouse), WarehouseDTO.class);
    }

    @Override
    public List<WarehouseDTO> findAll() {
        log.debug("getting findAll in warehouseService");
        List<Warehouse> all = warehouseDAO.findAll();
        if (all.isEmpty()) {
            log.warn("there are no any warehouse");
            throw new ServiceException("Warehouse is empty");
        }
        return mapListOfEntityToDTO(all);
    }

    @Override
    public Optional<WarehouseDTO> getById(long id) {
        log.debug("getting warehouse by id {}", id);
        Optional<Warehouse> optionalWarehouse = warehouseDAO.getById(id);
        log.debug("get optional value");
        Warehouse warehouseById;
        if (!optionalWarehouse.isPresent()) {
            log.warn("no warehouse with id {}",id);
            throw new ServiceException("No warehouse with this id");
        }
        warehouseById = optionalWarehouse.get();
        WarehouseDTO warehouseDTO = mapper.map(warehouseById, WarehouseDTO.class);
        log.debug("get warehouseDTO");
        return Optional.ofNullable(warehouseDTO);
    }

    @Override
    public WarehouseDTO update(WarehouseDTO warehouseDTO) {
        log.debug("starting update warehouseDTO");
        Warehouse updated = warehouseDAO.update(mapper.map(warehouseDTO, Warehouse.class));
        log.debug("updated warehouseDTO");
        return mapper.map(updated, WarehouseDTO.class);
    }

    @Override
    public int delete(long id) {
        log.debug("starting delete warehouse with id {}",id);
        return warehouseDAO.delete(id);
    }

    private List<WarehouseDTO> mapListOfEntityToDTO(List<Warehouse> all) {
        log.debug("start mapping List<Warehouse> in List<WarehouseDTO>");
        return all.stream().map(warehouse -> mapper.map(warehouse,WarehouseDTO.class))
                .collect(Collectors.toList());
    }
}
