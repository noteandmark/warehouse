package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.dao.ProductDAO;
import com.foxminded.andreimarkov.warehouse.model.Product;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class JdbcProductDAOImpl implements ProductDAO {
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_FIND_PRODUCT = "select id,code,name,description,quantity,price from schema.product where id = ?";
    private static final String SQL_DELETE_PRODUCT = "delete from schema.product where id = ?";
    private static final String SQL_UPDATE_PRODUCT = "update schema.product set code = ?, name = ?, description  = ?, quantity = ?, price = ? where id = ?";
    private static final String SQL_GET_ALL = "select id,code,name,description,quantity,price from schema.product ORDER BY id ASC";
    private static final String SQL_INSERT_PRODUCT = "insert into schema.product(code,name,description,quantity,price) values(?,?,?,?,?);";

    @Autowired
    public JdbcProductDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product save(Product product) {
        log.debug("save product");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(SQL_INSERT_PRODUCT, new String[] {"id"});
                    ps.setString(1,  product.getCode());
                    ps.setString(2,  product.getName());
                    ps.setString(3,  product.getDescription());
                    ps.setInt(4,  product.getQuantity());
                    ps.setInt(5,  product.getPrice());
                    return ps;
                },
                keyHolder);
        product.setId(keyHolder.getKey().longValue());
        log.debug("product {} saved", product.getName());
        return product;
    }

    @Override
    public List<Product> findAll() {
        log.debug("getting list of products in findAll");
        return jdbcTemplate.query(SQL_GET_ALL, new BeanPropertyRowMapper<Product>(Product.class));
    }

    @Override
    public Optional<Product> getById(Long id) {
        try {
            log.debug("try to get product by id {}", id);
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_PRODUCT,
                    new BeanPropertyRowMapper<Product>(Product.class), id));
        } catch (EmptyResultDataAccessException e) {
            log.error("get error: empty result, return optional.empty",e.getLocalizedMessage());
            return Optional.empty();
        }
    }

    @Override
    public Product update(Product product) {
        log.debug("update product");
        jdbcTemplate.update(SQL_UPDATE_PRODUCT, product.getCode(), product.getName(), product.getDescription(), product.getQuantity(),product.getPrice(),product.getId());
        log.debug("product {} updated",product.getName());
        return product;
    }

    @Override
    public int delete(Long id) {
        log.debug("delete product by id {}",id);
        return jdbcTemplate.update(SQL_DELETE_PRODUCT, id);
    }

}