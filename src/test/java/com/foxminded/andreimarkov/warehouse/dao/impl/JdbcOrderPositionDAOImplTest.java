package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.model.OrderPosition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import({JdbcOrderPositionDAOImpl.class, JdbcProductDAOImpl.class})
@Sql({"classpath:schema.sql", "classpath:startedData.sql"})
class JdbcOrderPositionDAOImplTest {

    @Autowired
    private JdbcOrderPositionDAOImpl orderPositionDAO;

    @Autowired
    private JdbcProductDAOImpl productDAO;

    @Test
    void save() {
        OrderPosition orderPosition = new OrderPosition();
        orderPosition.setAmount(150);
        orderPosition.setItem(productDAO.getById(1L).get());
        orderPositionDAO.save(orderPosition);
        assertNotNull(orderPosition);
        assertNotNull(orderPosition.getId());
        assertEquals(150,orderPosition.getAmount());
    }

    @Test
    void findAll() {
        assertNotNull(orderPositionDAO.findAll());
        assertEquals(1, orderPositionDAO.findAll().size());
    }

    @Test
    void getById() {
        Optional<Boolean> present = Optional.of(orderPositionDAO.getById(100000L).isPresent());
        assertFalse(present.get());
        assertNotNull(orderPositionDAO.getById(1L).get());
    }

    @Test
    void update() {
        OrderPosition orderPosition = new OrderPosition();
        orderPosition.setAmount(150);
        orderPosition.setItem(productDAO.getById(1L).get());
        orderPositionDAO.save(orderPosition);
        orderPosition.setAmount(200);
        OrderPosition updated = orderPositionDAO.update(orderPosition);
        assertNotNull(updated);
        assertNotNull(updated.getId());
        assertEquals(200,updated.getAmount());
    }

    @Test
    void delete() {
        assertEquals(1, orderPositionDAO.delete(1L));
        assertEquals(0, orderPositionDAO.delete(1L));
    }

}