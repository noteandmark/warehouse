package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcCatalogDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.CatalogDTO;
import com.foxminded.andreimarkov.warehouse.exceptions.ServiceException;
import com.foxminded.andreimarkov.warehouse.model.Catalog;
import com.foxminded.andreimarkov.warehouse.service.CatalogService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CatalogServiceImpl implements CatalogService {

    private static final Logger logger = LoggerFactory.getLogger(CatalogServiceImpl.class);
    private final JdbcCatalogDAOImpl catalogDAO;
    private final ModelMapper mapper;

    @Autowired
    public CatalogServiceImpl(JdbcCatalogDAOImpl catalogDAO, ModelMapper mapper) {
        this.catalogDAO = catalogDAO;
        this.mapper = mapper;
    }

    @Override
    public CatalogDTO save(CatalogDTO catalogDTO) {
        logger.debug("start method save CatalogDTO");
        Catalog catalog = mapper.map(catalogDTO, Catalog.class);
        logger.debug("saved catalog {}", catalog.getName());
        return mapper.map(catalogDAO.save(catalog), CatalogDTO.class);
    }

    @Override
    public List<CatalogDTO> findAll() {
        logger.debug("getting findAll");
        List<Catalog> all = catalogDAO.findAll();
        if (all.isEmpty()) {
            logger.warn("there are no any catalog");
            throw new ServiceException("Catalog is empty");
        }
        return mapListOfEntityToDTO(all);
    }

    @Override
    public Optional<CatalogDTO> getById(long id) {
        logger.debug("getting catalog by id {}", id);
        Optional<Catalog> optionalCatalog = catalogDAO.getById(id);
        logger.debug("get optional value");
        Catalog catalogById;
        if (!optionalCatalog.isPresent()) {
            logger.warn("no catalog with id {}",id);
            throw new ServiceException("No catalog with this id");
        }
        catalogById = optionalCatalog.get();
        CatalogDTO catalogDTO = mapper.map(catalogById, CatalogDTO.class);
        logger.debug("get catalogDTO");
        return Optional.ofNullable(catalogDTO);
    }

    @Override
    public CatalogDTO update(CatalogDTO catalogDTO) {
        logger.debug("starting update catalogDTO");
        Catalog updated = catalogDAO.update(mapper.map(catalogDTO, Catalog.class));
        logger.debug("updated catalogDTO");
        return mapper.map(updated, CatalogDTO.class);
    }

    @Override
    public int delete(long id) {
        logger.debug("starting delete catalog with id {}",id);
        return catalogDAO.delete(id);
    }

    private List<CatalogDTO> mapListOfEntityToDTO(List<Catalog> all) {
        logger.debug("start mapping List<Catalog> in List<CatalogDTO>");
        return all.stream().map(catalog -> mapper.map(catalog, CatalogDTO.class))
                .collect(Collectors.toList());
    }
}
