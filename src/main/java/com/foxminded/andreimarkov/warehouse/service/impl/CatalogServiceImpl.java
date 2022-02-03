package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcCatalogDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.CatalogDTO;
import com.foxminded.andreimarkov.warehouse.exceptions.ServiceException;
import com.foxminded.andreimarkov.warehouse.model.Catalog;
import com.foxminded.andreimarkov.warehouse.service.CatalogService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        log.debug("start method save CatalogDTO");
        Catalog catalog = mapper.map(catalogDTO, Catalog.class);
        log.debug("saved catalog {}", catalog.getName());
        return mapper.map(catalogDAO.save(catalog), CatalogDTO.class);
    }

    @Override
    public List<CatalogDTO> findAll() {
        log.debug("getting findAll in catalogService");
        List<Catalog> all = catalogDAO.findAll();
        if (all.isEmpty()) {
            log.warn("there are no any catalog");
            throw new ServiceException("Catalog is empty");
        }
        return mapListOfEntityToDTO(all);
    }

    @Override
    public Optional<CatalogDTO> getById(long id) {
        log.debug("getting catalog by id {}", id);
        Optional<Catalog> optionalCatalog = catalogDAO.getById(id);
        log.debug("get optional value");
        Catalog catalogById;
        if (!optionalCatalog.isPresent()) {
            log.warn("no catalog with id {}",id);
            throw new ServiceException("No catalog with this id");
        }
        catalogById = optionalCatalog.get();
        CatalogDTO catalogDTO = mapper.map(catalogById, CatalogDTO.class);
        log.debug("get catalogDTO");
        return Optional.ofNullable(catalogDTO);
    }

    @Override
    public CatalogDTO update(CatalogDTO catalogDTO) {
        log.debug("starting update catalogDTO");
        Catalog updated = catalogDAO.update(mapper.map(catalogDTO, Catalog.class));
        log.debug("updated catalogDTO");
        return mapper.map(updated, CatalogDTO.class);
    }

    @Override
    public int delete(long id) {
        log.debug("starting delete catalog with id {}",id);
        return catalogDAO.delete(id);
    }

    private List<CatalogDTO> mapListOfEntityToDTO(List<Catalog> all) {
        log.debug("start mapping List<Catalog> in List<CatalogDTO>");
        return all.stream().map(catalog -> mapper.map(catalog, CatalogDTO.class))
                .collect(Collectors.toList());
    }
}
