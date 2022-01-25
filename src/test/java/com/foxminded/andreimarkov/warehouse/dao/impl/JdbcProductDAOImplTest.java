package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(JdbcProductDAOImpl.class)
@Sql({"classpath:schema.sql", "classpath:startedData.sql"})
class JdbcProductDAOImplTest {

    @Autowired
    private JdbcProductDAOImpl repository;

    @Test
    void save() {
        Product product = new Product();
        product.setCode("350790");
        product.setName("Magnum WPF 2*18w");
        product.setDescription("fluorescent light bulb T8");
        product.setQuantity(22);
        product.setPrice(1500);
        repository.save(product);
        assertNotNull(product);
        assertNotNull(product.getId());
        assertEquals("Magnum WPF 2*18w",product.getName());
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
        Product product = new Product();
        product.setCode("350790");
        product.setName("Magnum WPF 2*18w");
        product.setDescription("fluorescent light bulb T8");
        product.setQuantity(22);
        product.setPrice(1500);
        repository.save(product);
        product.setName("Magnum fluorescent WPF 2*18w");
        Product updated = repository.update(product);
        assertNotNull(updated);
        assertNotNull(updated.getId());
        assertEquals("Magnum fluorescent WPF 2*18w",updated.getName());
    }

    @Test
    void delete() {
        assertEquals(1, repository.delete(1L));
        assertEquals(0, repository.delete(1L));
    }
}