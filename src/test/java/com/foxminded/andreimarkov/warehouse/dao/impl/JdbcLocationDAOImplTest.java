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
@Sql({"classpath:schema.sql", "classpath:resStartedData.sql"})
class JdbcLocationDAOImplTest {

    @Autowired
    private JdbcLocationDAOImpl repository;

    @Test
    void save() {
        Location location = new Location();
        location.setWarehouseName("83 warehouse");
        repository.save(location);
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
        repository.save(location);
        Location updated = repository.update(location);
        assertNotNull(updated);
        assertNotNull(updated.getId());
        assertEquals("83 warehouse",updated.getWarehouseName());
    }

    @Test
    void delete() {
        Location location = new Location();
        location.setWarehouseName("additional");
        repository.save(location);
        assertEquals(1, repository.delete(location.getId()));
        assertEquals(0, repository.delete(100L));
    }
}