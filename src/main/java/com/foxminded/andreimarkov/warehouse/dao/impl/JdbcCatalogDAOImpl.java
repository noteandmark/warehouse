package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.dao.CatalogDAO;
import com.foxminded.andreimarkov.warehouse.model.Catalog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCatalogDAOImpl implements CatalogDAO {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCatalogDAOImpl.class);
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_FIND_CATALOG = "select id,name from schema.catalog where id = ?";
    private static final String SQL_DELETE_CATALOG = "delete from schema.catalog where id = ?";
    private static final String SQL_UPDATE_CATALOG = "update schema.catalog set name = ? where id = ?";
    private static final String SQL_GET_ALL = "select name from schema.catalog";
    private static final String SQL_INSERT_CATALOG = "insert into schema.catalog(name) values(?);";

    @Autowired
    public JdbcCatalogDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Catalog save(Catalog catalog) {
        logger.debug("save catalog");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(SQL_INSERT_CATALOG, new String[] {"id"});
                    ps.setString(1,  catalog.getName());
                    return ps;
                },
                keyHolder);
        catalog.setId(keyHolder.getKey().longValue());
        logger.debug("catalog {} saved", catalog.getName());
        return catalog;
    }

    @Override
    public List<Catalog> findAll() {
        logger.debug("getting list of catalogs in findAll");
        return jdbcTemplate.query(SQL_GET_ALL, new BeanPropertyRowMapper<>(Catalog.class));
    }

    @Override
    public Optional<Catalog> getById(Long id) {
        try {
            logger.debug("try to get catalog by id {}", id);
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_CATALOG,
                    new BeanPropertyRowMapper<>(Catalog.class), id));
        } catch (EmptyResultDataAccessException e) {
            logger.error("get error: empty result, return optional.empty",e.getLocalizedMessage());
            return Optional.empty();
        }
    }

    @Override
    public Catalog update(Catalog catalog) {
        logger.debug("update catalog");
        jdbcTemplate.update(SQL_UPDATE_CATALOG, catalog.getName(),catalog.getId());
        logger.debug("catalog updated");
        return catalog;
    }

    @Override
    public int delete(Long id) {
        logger.debug("delete catalog by id {}",id);
        return jdbcTemplate.update(SQL_DELETE_CATALOG, id);
    }

}