package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.dao.AbstractDAO;
import com.foxminded.andreimarkov.warehouse.model.Customer;
import com.foxminded.andreimarkov.warehouse.model.Person;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JdbcPersonDAOImplTest {

    private EmbeddedDatabase embeddedDatabase;

    private JdbcTemplate jdbcTemplate;

    private AbstractDAO personDAO;

    @BeforeEach
    public void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()// add scripts schema.sql and data.sql
                .setType(EmbeddedDatabaseType.H2)// use base H2
                .build();
        jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        personDAO = new JdbcPersonDAOImpl(jdbcTemplate);
    }

    @Test
    void create() {
        System.out.println("personDAO is instanse of " + personDAO.getClass().isInstance(JdbcPersonDAOImpl.class));
        Customer person = new Person(100L,"Sam", "Becket", 1000, "75, Lincoln drive", "3801112233");
        System.out.println("person -> " + person.toString());
        System.out.println("person.address = " + person.getAddress());
        personDAO.create(person);
        System.out.println("after personDAO.create(person)");
        Person actual =  (Person) personDAO.getById(100L).get();
        assertNotNull(actual);
        assertEquals("Sam",actual.getFirstName());
    }

    @Test
    @Disabled
    void findAll() {

        assertNotNull(personDAO.findAll());
        assertEquals(2, personDAO.findAll().size());
    }

    @Test
    @Disabled
    void getById() {
    }

    @Test
    @Disabled
    void update() {
    }

    @Test
    @Disabled
    void delete() {
    }

    @AfterEach
    public void tearDown() {
        embeddedDatabase.shutdown();
    }
}