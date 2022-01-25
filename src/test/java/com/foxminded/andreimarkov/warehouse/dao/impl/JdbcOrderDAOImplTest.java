package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(JdbcOrderDAOImpl.class)
@Sql({"classpath:schema.sql", "classpath:startedData.sql"})
class JdbcOrderDAOImplTest {

    @Autowired
    private JdbcOrderDAOImpl repository;

    @Test
    void save() {
        Order order = new Order();
        order.setStatus("not_processed");
        order.setDate("09-01-2022 19:14:00");
        repository.save(order);
        assertNotNull(order);
        assertNotNull(order.getId());
        assertEquals("not_processed",order.getStatus());
    }

    @Test
    void findAll() {
        assertNotNull(repository.findAll());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void getById() {
        Optional<Boolean> present = Optional.of(repository.getById(100000L).isPresent());
        assertFalse(present.get());
        assertNotNull(repository.getById(1L).get());
    }

    @Test
    void update() {
        Order order = new Order();
        order.setStatus("not_processed");
        order.setDate("10-01-2022 10:00:00");
        repository.save(order);
        order.setStatus("processed");
        order.setDate("10-01-2022 11:00:00");
        Order updated = repository.update(order);
        assertNotNull(updated);
        assertNotNull(updated.getId());
        assertEquals("processed",updated.getStatus());
        assertEquals("10-01-2022 11:00:00",updated.getDate());
    }

    @Test
    void delete() {
        assertEquals(1, repository.delete(1L));
        assertEquals(0, repository.delete(1L));
    }

}