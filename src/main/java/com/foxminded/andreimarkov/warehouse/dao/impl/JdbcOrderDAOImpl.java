package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.dao.OrderDAO;
import com.foxminded.andreimarkov.warehouse.model.Location;
import com.foxminded.andreimarkov.warehouse.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcOrderDAOImpl implements OrderDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcOrderDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_FIND_ORDER = "select id,status,date from schema.orders where id = ?";
    private static final String SQL_DELETE_ORDER = "delete from schema.orders where id = ?";
    private static final String SQL_UPDATE_ORDER = "update schema.orders set status = ?, date = ? where id = ?";
    private static final String SQL_GET_ALL = "select status,date from schema.orders";
    private static final String SQL_INSERT_ORDER = "insert into schema.orders (status, date) values(?,?);";

    @Override
    public Order save(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(SQL_INSERT_ORDER, new String[]{"id"});
                    ps.setString(1, order.getStatus());
                    ps.setString(2, order.getDate());
                    return ps;
                },
                keyHolder);
        order.setId(keyHolder.getKey().longValue());
        return order;
    }

    @Override
    public Optional<Order> getById(Long id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_ORDER,
                    new BeanPropertyRowMapper<Order>(Order.class), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL, new BeanPropertyRowMapper<Order>(Order.class));
    }

    @Override
    public Order update(Order order) {
        jdbcTemplate.update(SQL_UPDATE_ORDER, order.getStatus(), order.getDate(), order.getId());
        return order;
    }

    public int delete(Long id) {
        return jdbcTemplate.update(SQL_DELETE_ORDER, id);
    }
}
