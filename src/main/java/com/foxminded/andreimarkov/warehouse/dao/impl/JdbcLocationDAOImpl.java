package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.dao.LocationDAO;
import com.foxminded.andreimarkov.warehouse.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class JdbcLocationDAOImpl implements LocationDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcLocationDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_FIND_LOCATION = "select id,warehouse_name,shelf_number from schema.location where id = ?";
    private static final String SQL_DELETE_LOCATION = "delete from schema.location where id = ?";
    private static final String SQL_UPDATE_LOCATION = "update schema.location set warehouse_name = ?, shelf_number = ? where id = ?";
    private static final String SQL_GET_ALL = "select warehouse_name,shelf_number from schema.location";
    private static final String SQL_INSERT_LOCATION = "insert into schema.location(warehouse_name, shelf_number) values(?,?);";

    @Override
    public Location create(Location location) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(SQL_INSERT_LOCATION, new String[]{"id"});
                    ps.setString(1, location.getWarehouseName());
                    ps.setInt(2, location.getShelfNumber());
                    return ps;
                },
                keyHolder);
        location.setId(keyHolder.getKey().longValue());
        return location;
    }

    @Override
    public List<Location> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL, new BeanPropertyRowMapper<Location>(Location.class));
    }

    @Override
    public Optional<Location> getById(Long id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_LOCATION,
                    new BeanPropertyRowMapper<Location>(Location.class), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Location update(Location location) {
        jdbcTemplate.update(SQL_UPDATE_LOCATION, location.getWarehouseName(), location.getShelfNumber(), location.getId());
        return location;
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update(SQL_DELETE_LOCATION, id);
    }

}