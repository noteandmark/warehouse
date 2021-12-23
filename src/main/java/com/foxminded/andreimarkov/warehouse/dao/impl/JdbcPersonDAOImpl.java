package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.dao.AbstractDAO;
import com.foxminded.andreimarkov.warehouse.dao.PersonDAO;
import com.foxminded.andreimarkov.warehouse.model.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JdbcPersonDAOImpl implements PersonDAO {

    private JdbcTemplate jdbcTemplate;

    public JdbcPersonDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final String SQL_FIND_PERSON = "select * from schema.person where id = ?";
    private final String SQL_DELETE_PERSON = "delete from schema.person where id = ?";
    private final String SQL_UPDATE_PERSON = "update schema.person set first_name = ?, sur_name = ?, balance  = ?, address = ?, phone = ? where id = ?";
    private final String SQL_GET_ALL = "select first_name,sur_name,balance,address,phone from schema.person";
    private final String SQL_INSERT_PERSON = "insert into schema.person(first_name, sur_name, balance, address, phone) values(?,?,?,?,?)";

    @Override
    public Optional<Person> create(Person person) {
        System.out.println("in create method");
        jdbcTemplate.update(SQL_INSERT_PERSON, person.getFirstName(), person.getSurName(), person.getBalance(), person.getAddress(), person.getPhone());
        return getById(person.getId());
    }

    @Override
    public List<Person> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL, new BeanPropertyRowMapper<Person>(Person.class));
    }

    @Override
    public Optional<Person> getById(Long id) {
        Person person = new Person();
        person = jdbcTemplate.queryForObject(SQL_FIND_PERSON, new Object[]{id}, new BeanPropertyRowMapper<Person>(Person.class));
        if (!(person == null)) {
            return Optional.ofNullable(person);
        } else
            return Optional.empty();
    }

    @Override
    public Optional<Person> update(Person person) {
        jdbcTemplate.update(SQL_UPDATE_PERSON, person.getFirstName(), person.getSurName(), person.getBalance(), person.getAddress(), person.getPhone());
        return getById(person.getId());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update(SQL_DELETE_PERSON, id);
    }

}