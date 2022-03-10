package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(JdbcProductDAOImpl.class)
@Sql({"classpath:schema.sql", "classpath:resStartedData.sql"})
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
        product.setCatalogId(1);
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
        product.setCatalogId(1);
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

    @Test
    void getProductsByCatalogId() {
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();

        product1.setCode("350790");
        product1.setName("Magnum WPF 2*18w");
        product1.setDescription("fluorescent light bulb T8");
        product1.setQuantity(22);
        product1.setPrice(1500);
        product1.setCatalogId(1);

        product2.setCode("350791");
        product2.setName("Magnum WPF 2*20w");
        product2.setDescription("fluorescent light bulb T8");
        product2.setQuantity(20);
        product2.setPrice(1700);
        product2.setCatalogId(1);

        product3.setCode("350792");
        product3.setName("Magnum WPF 2*25w");
        product3.setDescription("fluorescent light bulb T8");
        product3.setQuantity(5);
        product3.setPrice(2500);
        product3.setCatalogId(2);

        repository.save(product1);
        repository.save(product2);
        repository.save(product3);

        List<Product> expected = new ArrayList<>();
        expected.add(product3);

        List<Product> actual = repository.getProductsByCatalogId(2);
        assertEquals(expected, actual);
    }
}