package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.model.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(JdbcCompanyDAOImpl.class)
@Sql({"classpath:schema.sql", "classpath:startedData.sql"})
class JdbcCompanyDAOImplTest {

    @Autowired
    private JdbcCompanyDAOImpl repository;

    @Test
    void create() {
        Company company = new Company();
        company.setName("New Industry Tech");
        company.setBalance(1300);
        company.setAddress("75, Lincoln drive");
        company.setPhone("3807772233");
        repository.create(company);
        assertNotNull(company);
        assertNotNull(company.getId());
        assertEquals("New Industry Tech",company.getName());
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
        Company company = new Company();
        company.setName("IBC");
        company.setBalance(300);
        company.setAddress("5, Foo drive");
        company.setPhone("70801112233");
        repository.create(company);
        company.setName("IBC NEW");
        Company updated = repository.update(company);
        assertNotNull(updated);
        assertNotNull(updated.getId());
        assertEquals("IBC NEW",updated.getName());
    }

    @Test
    void delete() {
        assertEquals(1, repository.delete(1L));
        assertEquals(0, repository.delete(1L));
    }
}