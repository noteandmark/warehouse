package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.dao.WarehouseDAO;
import com.foxminded.andreimarkov.warehouse.model.Warehouse;
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
public class JdbcWarehouseDAOImpl implements WarehouseDAO {
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_FIND_WAREHOUSE = "select id,name from schema.warehouse where id = ?";
    private static final String SQL_DELETE_WAREHOUSE = "delete from schema.warehouse where id = ?";
    private static final String SQL_UPDATE_WAREHOUSE = "update schema.warehouse set name = ? where id = ?";
    private static final String SQL_GET_ALL = "select name from schema.warehouse";
    private static final String SQL_INSERT_WAREHOUSE = "insert into schema.warehouse(name) values(?);";

    @Autowired
    public JdbcWarehouseDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Warehouse save(Warehouse warehouse) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(SQL_INSERT_WAREHOUSE, new String[] {"id"});
                    ps.setString(1,  warehouse.getName());
                    return ps;
                },
                keyHolder);
        warehouse.setId(keyHolder.getKey().longValue());
        return warehouse;
    }

    @Override
    public List<Warehouse> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL, new BeanPropertyRowMapper<>(Warehouse.class));
    }

    @Override
    public Optional<Warehouse> getById(Long id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_WAREHOUSE,
                    new BeanPropertyRowMapper<>(Warehouse.class), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Warehouse update(Warehouse warehouse) {
        jdbcTemplate.update(SQL_UPDATE_WAREHOUSE, warehouse.getName(),warehouse.getId());
        return warehouse;
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update(SQL_DELETE_WAREHOUSE, id);
    }

}
