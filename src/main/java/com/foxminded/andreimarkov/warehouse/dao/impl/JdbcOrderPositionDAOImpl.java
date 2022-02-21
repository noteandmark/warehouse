package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.dao.OrderPositionDAO;
import com.foxminded.andreimarkov.warehouse.model.OrderPosition;
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
public class JdbcOrderPositionDAOImpl implements OrderPositionDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcOrderPositionDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_FIND_ORDERPOSITION = "select id,amount, product_id from schema.orderposition where id = ?";
    private static final String SQL_DELETE_ORDERPOSITION = "delete from schema.orderposition where id = ?";
    private static final String SQL_UPDATE_ORDERPOSITION = "update schema.orderposition set amount = ?, product_id = ? where id = ?";
    private static final String SQL_GET_ALL = "select id,amount, product_id from schema.orderposition ORDER BY id ASC";
    private static final String SQL_INSERT_ORDERPOSITION = "insert into schema.orderposition (amount, product_id) values(?,?);";

    @Override
    public OrderPosition save(OrderPosition orderPosition) {
        log.debug("save orderPosition");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(SQL_INSERT_ORDERPOSITION, new String[]{"id"});
                    ps.setInt(1, orderPosition.getAmount());
                    ps.setLong(2, orderPosition.getProduct().getId());
                    return ps;
                },
                keyHolder);
        orderPosition.setId(keyHolder.getKey().longValue());
        log.debug("orderPosition id {} saved", orderPosition.getId());
        return orderPosition;
    }

    @Override
    public Optional<OrderPosition> getById(Long id) {
        try {
            log.debug("try to get orderPosition by id {}", id);
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_ORDERPOSITION,
                    new BeanPropertyRowMapper<OrderPosition>(OrderPosition.class), id));
        } catch (EmptyResultDataAccessException e) {
            log.error("get error: empty result, return optional.empty",e.getLocalizedMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<OrderPosition> findAll() {
        log.debug("getting list of orderPositions in findAll");
        return jdbcTemplate.query(SQL_GET_ALL, new BeanPropertyRowMapper<OrderPosition>(OrderPosition.class));
    }

    @Override
    public OrderPosition update(OrderPosition orderPosition) {
        log.debug("update orderPosition");
        jdbcTemplate.update(SQL_UPDATE_ORDERPOSITION, orderPosition.getAmount(), orderPosition.getProduct().getId(), orderPosition.getId());
        log.debug("orderPosition id {} updated",orderPosition.getId());
        return orderPosition;
    }

    public int delete(Long id) {
        log.debug("delete orderPosition by id {}",id);
        return jdbcTemplate.update(SQL_DELETE_ORDERPOSITION, id);
    }
}
