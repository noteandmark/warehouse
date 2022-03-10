package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.model.Warehouse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(JdbcWarehouseDAOImpl.class)
@Sql({"classpath:schema.sql", "classpath:resStartedData.sql"})
class JdbcWarehouseDAOImplTest {

    @Autowired
    private JdbcWarehouseDAOImpl repository;

    @Test
    void save() {
        Warehouse warehouse = new Warehouse();
        warehouse.setName("main warehouse");
        repository.save(warehouse);
        assertNotNull(warehouse);
        assertNotNull(warehouse.getId());
        assertEquals("main warehouse",warehouse.getName());
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
        Warehouse warehouse = new Warehouse();
        warehouse.setName("additional warehouse");
        repository.save(warehouse);
        warehouse.setName("2nd additional warehouse");
        Warehouse updated = repository.update(warehouse);
        assertNotNull(updated);
        assertNotNull(updated.getId());
        assertEquals("2nd additional warehouse",updated.getName());
    }

    @Test
    void delete() {
        assertEquals(1, repository.delete(1L));
        assertEquals(0, repository.delete(1L));
    }

}