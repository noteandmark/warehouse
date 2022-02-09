package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.dao.LocationDAO;
import com.foxminded.andreimarkov.warehouse.model.Location;
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
public class JdbcLocationDAOImpl implements LocationDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcLocationDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_FIND_LOCATION = "select id,warehouse_name,shelf_number from schema.location where id = ?";
    private static final String SQL_DELETE_LOCATION = "delete from schema.location where id = ?";
    private static final String SQL_UPDATE_LOCATION = "update schema.location set warehouse_name = ?, shelf_number = ? where id = ?";
    private static final String SQL_GET_ALL = "select id,warehouse_name,shelf_number from schema.location";
    private static final String SQL_INSERT_LOCATION = "insert into schema.location(warehouse_name, shelf_number) values(?,?);";

    @Override
    public Location save(Location location) {
        log.debug("save location");
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
        log.debug("location {} saved", location.getWarehouseName());
        return location;
    }

    @Override
    public List<Location> findAll() {
        log.debug("getting list of locations in findAll");
        return jdbcTemplate.query(SQL_GET_ALL, new BeanPropertyRowMapper<Location>(Location.class));
    }

    @Override
    public Optional<Location> getById(Long id) {
        try {
            log.debug("try to get location by id {}", id);
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_LOCATION,
                    new BeanPropertyRowMapper<Location>(Location.class), id));
        } catch (EmptyResultDataAccessException e) {
            log.error("get error: empty result, return optional.empty",e.getLocalizedMessage());
            return Optional.empty();
        }
    }

    @Override
    public Location update(Location location) {
        log.debug("update location");
        jdbcTemplate.update(SQL_UPDATE_LOCATION, location.getWarehouseName(), location.getShelfNumber(), location.getId());
        log.debug("location {} updated",location.getWarehouseName());
        return location;
    }

    @Override
    public int delete(Long id) {
        log.debug("delete location by id {}",id);
        return jdbcTemplate.update(SQL_DELETE_LOCATION, id);
    }

}
