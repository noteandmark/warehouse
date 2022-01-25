package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.dao.PersonDAO;
import com.foxminded.andreimarkov.warehouse.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcPersonDAOImpl implements PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcPersonDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_FIND_PERSON = "select id,first_name,sur_name,balance,address,phone from schema.person where id = ?";
    private static final String SQL_DELETE_PERSON = "delete from schema.person where id = ?";
    private static final String SQL_UPDATE_PERSON = "update schema.person set first_name = ?, sur_name = ?, balance  = ?, address = ?, phone = ? where id = ?";
    private static final String SQL_GET_ALL = "select first_name,sur_name,balance,address,phone from schema.person";
    private static final String SQL_INSERT_PERSON = "insert into schema.person(first_name, sur_name, balance, address, phone) values(?,?,?,?,?);";

    @Override
    public Person save(Person person) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(SQL_INSERT_PERSON, new String[] {"id"});
                    ps.setString(1,  person.getFirstName());
                    ps.setString(2,  person.getSurName());
                    ps.setInt(3,  person.getBalance());
                    ps.setString(4,  person.getAddress());
                    ps.setString(5,  person.getPhone());
                    return ps;
                },
                keyHolder);
        person.setId(keyHolder.getKey().longValue());
        return person;
    }

    @Override
    public List<Person> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL, new BeanPropertyRowMapper<Person>(Person.class));
    }

    @Override
    public Optional<Person> getById(Long id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_PERSON,
                    new BeanPropertyRowMapper<Person>(Person.class),id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Person update(Person person) {
        jdbcTemplate.update(SQL_UPDATE_PERSON, person.getFirstName(), person.getSurName(), person.getBalance(), person.getAddress(), person.getPhone(),person.getId());
        return person;
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update(SQL_DELETE_PERSON, id);
    }

}