package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.model.Catalog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
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
    void create() {
        Catalog catalog = new Catalog();
        catalog.setName("halogen lamps");
        repository.create(catalog);
        assertNotNull(catalog);
        assertNotNull(catalog.getId());
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
        repository.create(catalog);
        catalog.setName("led lamps");
        Catalog updated = repository.update(catalog);
        assertNotNull(updated);
        assertNotNull(updated.getId());
        assertEquals("led lamps",updated.getName());
    }

    @Test
    void delete() {
        assertEquals(1, repository.delete(1L));
        assertEquals(0, repository.delete(1L));
    }

    @Test
    void addProductToCatalog() {
        repository.addProductToCatalog(1L, 1L);
        assertTrue(repository.getProductFromCatalog(1L).stream().anyMatch(product -> product.getId() == 1L));
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            repository.addProductToCatalog(1L,100000L);
        });
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            repository.addProductToCatalog(100000L,1L);
        });
    }

    @Test
    void deleteProductFromCatalog() {
        repository.addProductToCatalog(1L, 1L);
        repository.deleteProductFromCatalog(1L,1L);
        assertTrue(repository.getProductFromCatalog(1L).stream().noneMatch(product -> product.getId() == 1L));
    }

    @Test
    void getProductFromCatalog() {
        repository.addProductToCatalog(1L, 1L);
        int count = repository.getProductFromCatalog(1L).size();
        assertEquals(1, count);
    }
}