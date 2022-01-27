package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcWarehouseDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.WarehouseDTO;
import com.foxminded.andreimarkov.warehouse.model.Warehouse;
import com.foxminded.andreimarkov.warehouse.service.WarehouseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final JdbcWarehouseDAOImpl warehouseDAO;
    private final ModelMapper mapper;

    @Autowired
    public WarehouseServiceImpl(JdbcWarehouseDAOImpl warehouseDAO, ModelMapper mapper) {
        this.warehouseDAO = warehouseDAO;
        this.mapper = mapper;
    }

    @Override
    public WarehouseDTO save(WarehouseDTO catalogDTO) {
        Warehouse catalog = mapper.map(catalogDTO, Warehouse.class);
        return mapper.map(warehouseDAO.save(catalog), WarehouseDTO.class);
    }

    @Override
    public List<WarehouseDTO> findAll() {
        List<Warehouse> all = warehouseDAO.findAll();
        return mapListOfEntityToDTO(all);
    }

    @Override
    public Optional<WarehouseDTO> getById(long id) {
        Warehouse warehouseById = warehouseDAO.getById(id).get();
        WarehouseDTO warehouseDTO = mapper.map(warehouseById, WarehouseDTO.class);
        return Optional.ofNullable(warehouseDTO);
    }

    @Override
    public WarehouseDTO update(WarehouseDTO warehouseDTO) {
        Warehouse updated = warehouseDAO.update(mapper.map(warehouseDTO, Warehouse.class));
        return mapper.map(updated, WarehouseDTO.class);
    }

    @Override
    public int delete(long id) {
        return warehouseDAO.delete(id);
    }

    private List<WarehouseDTO> mapListOfEntityToDTO(List<Warehouse> all) {
        return all.stream().map(warehouse -> mapper.map(warehouse,WarehouseDTO.class))
                .collect(Collectors.toList());
    }
}
