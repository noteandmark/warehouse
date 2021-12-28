package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.model.Person;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(JdbcPersonDAOImpl.class)
@Sql({"classpath:schema.sql", "classpath:data.sql"})
class JdbcPersonDAOImplTest {

    @Autowired
    private JdbcPersonDAOImpl repository;

    @Test
    void create() {
        Person person = new Person();
        person.setFirstName("Sam");
        person.setSurName("Becket");
        person.setBalance(1300);
        person.setAddress("75, Lincoln drive");
        person.setPhone("3801112233");
        repository.create(person);
        assertNotNull(person);
        assertNotNull(person.getId());
        assertEquals("Sam",person.getFirstName());
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
        Person person = new Person();
        person.setFirstName("Roy");
        person.setSurName("Paulson");
        person.setBalance(300);
        person.setAddress("5, Foo drive");
        person.setPhone("70801112233");
        repository.create(person);
        person.setFirstName("Gregory");
        Person updated = repository.update(person);
        assertNotNull(updated);
        assertNotNull(updated.getId());
        assertEquals("Gregory",updated.getFirstName());
    }

    @Test
    void delete() {
        assertEquals(1, repository.delete(1L));
        assertEquals(0, repository.delete(1L));
    }

}