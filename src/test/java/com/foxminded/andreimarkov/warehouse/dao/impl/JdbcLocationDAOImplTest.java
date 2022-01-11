package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.model.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(JdbcLocationDAOImpl.class)
@Sql({"classpath:schema.sql", "classpath:startedData.sql"})
class JdbcLocationDAOImplTest {

    @Autowired
    private JdbcLocationDAOImpl repository;

    @Test
    void create() {
        Location location = new Location();
        location.setWarehouseName("83 warehouse");
        location.setShelfNumber(15);
        repository.create(location);
        assertNotNull(location);
        assertNotNull(location.getId());
        assertEquals("83 warehouse",location.getWarehouseName());
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
        Location location = new Location();
        location.setWarehouseName("83 warehouse");
        location.setShelfNumber(10);
        repository.create(location);
        location.setShelfNumber(20);
        Location updated = repository.update(location);
        assertNotNull(updated);
        assertNotNull(updated.getId());
        assertEquals(20,updated.getShelfNumber());
    }

    @Test
    void delete() {
        assertEquals(1, repository.delete(1L));
        assertEquals(0, repository.delete(1L));
    }
}