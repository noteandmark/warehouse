package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.dao.OrderPositionDAO;
import com.foxminded.andreimarkov.warehouse.model.OrderPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class JdbcOrderPositionDAOImpl implements OrderPositionDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcOrderPositionDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_FIND_ORDERPOSITION = "select id,amount, product_id from schema.orderposition where id = ?";
    private static final String SQL_DELETE_ORDERPOSITION = "delete from schema.orderposition where id = ?";
    private static final String SQL_UPDATE_ORDERPOSITION = "update schema.orderposition set amount = ?, product_id = ? where id = ?";
    private static final String SQL_GET_ALL = "select amount, product_id from schema.orderposition";
    private static final String SQL_INSERT_ORDERPOSITION = "insert into schema.orderposition (amount, product_id) values(?,?);";

    @Override
    public OrderPosition save(OrderPosition orderPosition) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(SQL_INSERT_ORDERPOSITION, new String[]{"id"});
                    ps.setInt(1, orderPosition.getAmount());
                    ps.setLong(2, orderPosition.getItem().getId());
                    return ps;
                },
                keyHolder);
        orderPosition.setId(keyHolder.getKey().longValue());
        return orderPosition;
    }

    @Override
    public Optional<OrderPosition> getById(Long id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_ORDERPOSITION,
                    new BeanPropertyRowMapper<OrderPosition>(OrderPosition.class), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<OrderPosition> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL, new BeanPropertyRowMapper<OrderPosition>(OrderPosition.class));
    }

    @Override
    public OrderPosition update(OrderPosition orderPosition) {
        jdbcTemplate.update(SQL_UPDATE_ORDERPOSITION, orderPosition.getAmount(), orderPosition.getItem().getId(), orderPosition.getId());
        return orderPosition;
    }

    public int delete(Long id) {
        return jdbcTemplate.update(SQL_DELETE_ORDERPOSITION, id);
    }
}
