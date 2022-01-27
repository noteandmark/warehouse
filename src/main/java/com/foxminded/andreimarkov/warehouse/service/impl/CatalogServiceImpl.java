package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcCatalogDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.CatalogDTO;
import com.foxminded.andreimarkov.warehouse.model.Catalog;
import com.foxminded.andreimarkov.warehouse.service.CatalogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final JdbcCatalogDAOImpl catalogDAO;
    private final ModelMapper mapper;

    @Autowired
    public CatalogServiceImpl(JdbcCatalogDAOImpl catalogDAO, ModelMapper mapper) {
        this.catalogDAO = catalogDAO;
        this.mapper = mapper;
    }

    @Override
    public CatalogDTO save(CatalogDTO catalogDTO) {
        Catalog catalog = mapper.map(catalogDTO, Catalog.class);
        return mapper.map(catalogDAO.save(catalog), CatalogDTO.class);
    }

    @Override
    public List<CatalogDTO> findAll() {
        List<Catalog> all = catalogDAO.findAll();
        return mapListOfEntityToDTO(all);
    }

    @Override
    public Optional<CatalogDTO> getById(long id) {
        Catalog catalogById = catalogDAO.getById(id).get();
        CatalogDTO catalogDTO = mapper.map(catalogById, CatalogDTO.class);
        return Optional.ofNullable(catalogDTO);
    }

    @Override
    public CatalogDTO update(CatalogDTO catalogDTO) {
        Catalog updated = catalogDAO.update(mapper.map(catalogDTO, Catalog.class));
        return mapper.map(updated, CatalogDTO.class);
    }

    @Override
    public int delete(long id) {
        return catalogDAO.delete(id);
    }

    private List<CatalogDTO> mapListOfEntityToDTO(List<Catalog> all) {
        return all.stream().map(catalog -> mapper.map(catalog,CatalogDTO.class))
                .collect(Collectors.toList());
    }
}
