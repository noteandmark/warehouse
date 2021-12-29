package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.dao.CatalogDAO;
import com.foxminded.andreimarkov.warehouse.model.Catalog;
import com.foxminded.andreimarkov.warehouse.model.Product;
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
    private final JdbcTemplate jdbcTemplate;
    private final JdbcProductDAOImpl goodsDao;

    private static final String SQL_FIND_CATALOG = "select name from schema.catalog where id = ?";
    private static final String SQL_DELETE_CATALOG = "delete from schema.catalog where id = ?";
    private static final String SQL_UPDATE_CATALOG = "update schema.catalog set name = ? where id = ?";
    private static final String SQL_GET_ALL = "select name from schema.catalog";
    private static final String SQL_INSERT_CATALOG = "insert into schema.catalog(name) values(?);";
    private static final String SQL_INSERT_PRODUCT = "insert into schema.product_catalog(product_id, catalog_id) VALUES (?,?)";
    private static final String SQL_DELETE_PRODUCT = "delete from schema.product_catalog WHERE product_id = ? AND catalog_id = ?";
    private static final String SQL_GET_ALL_PRODUCTS = "select code,name,description,quantity,price from schema.product JOIN schema.product_catalog on schema.product_catalog.product_id=schema.product.id WHERE schema.product_catalog.catalog_id = ?";

    @Autowired
    public JdbcCatalogDAOImpl(JdbcTemplate jdbcTemplate, JdbcProductDAOImpl goodsDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.goodsDao = goodsDao;
    }

    @Override
    public Catalog create(Catalog catalog) {
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
        return catalog;
    }

    @Override
    public List<Catalog> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL, new BeanPropertyRowMapper<>(Catalog.class));
    }

    @Override
    public Optional<Catalog> getById(Long id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_CATALOG,
                    new BeanPropertyRowMapper<>(Catalog.class), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Catalog update(Catalog catalog) {
        jdbcTemplate.update(SQL_UPDATE_CATALOG, catalog.getName(),catalog.getId());
        return catalog;
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update(SQL_DELETE_CATALOG, id);
    }

    public void addProductToCatalog(Long productId, Long catalogId) {
        jdbcTemplate.update(SQL_INSERT_PRODUCT, productId, catalogId);
    }

    public void deleteProductFromCatalog(Long productId, Long catalogId) {
        jdbcTemplate.update(SQL_DELETE_PRODUCT, productId, catalogId);
    }

    public List<Product> getProductFromCatalog (Long catalogId) {
        return jdbcTemplate.query(SQL_GET_ALL_PRODUCTS, new BeanPropertyRowMapper<>(Product.class), catalogId);
    }
}
