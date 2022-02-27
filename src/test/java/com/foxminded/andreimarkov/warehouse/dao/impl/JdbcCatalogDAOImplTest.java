package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.model.Catalog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(JdbcCatalogDAOImpl.class)
@Sql({"classpath:schema.sql", "classpath:startedData.sql"})
class JdbcCatalogDAOImplTest {

    @Autowired
    private JdbcCatalogDAOImpl repository;

    @Test
    void save() {
        Catalog catalog = new Catalog();
        catalog.setName("halogen lamps");
        repository.save(catalog);
        assertNotNull(catalog);
        assertNotNull(catalog.getCatalogId());
        assertEquals("halogen lamps",catalog.getName());
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
        Catalog catalog = new Catalog();
        catalog.setName("lamps");
        repository.save(catalog);
        catalog.setName("led lamps");
        Catalog updated = repository.update(catalog);
        assertNotNull(updated);
        assertNotNull(updated.getCatalogId());
        assertEquals("led lamps",updated.getName());
    }

    @Test
    void delete() {
        assertEquals(1, repository.delete(1L));
        assertEquals(0, repository.delete(1L));
    }

}