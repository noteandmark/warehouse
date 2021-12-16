package com.foxminded.andreimarkov.warehouse.dao;

import com.foxminded.andreimarkov.warehouse.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class PersonDAOImpl implements PersonDAO {

    JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_PERSON = "select * from person where id = ?";
    private final String SQL_DELETE_PERSON = "delete from person where id = ?";
    private final String SQL_UPDATE_PERSON = "update person set first_name = ?, sur_name = ?, balance  = ?, address = ?, phone = ? where id = ?";
    private final String SQL_GET_ALL = "select * from person";
    private final String SQL_INSERT_PERSON = "insert into person(id, first_name, sur_name, balance, address, phone) values(?,?,?,?,?,?)";

    @Autowired
    public PersonDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Person> findAll() {
        return jdbcTemplate.query("select * from person",
                new PersonRowMapper());
    }

    @Override
    public Person getPersonById(Long id) {
        return jdbcTemplate.queryForObject(SQL_FIND_PERSON, new Object[]{id}, new PersonRowMapper());
    }

    @Override
    public List<Person> getAllPersons() {
        return null;
    }

    @Override
    public boolean deletePerson(Person person) {
        return false;
    }

    @Override
    public boolean updatePerson(Person person) {
        return false;
    }

    @Override
    public boolean createPerson(Person person) {
        return false;
    }

    class PersonRowMapper implements RowMapper<Person> {
        @Override
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
            Person person = new Person();
            person.setId(rs.getInt("id"));
            person.setFirstName(rs.getString("first_name"));
            person.setSurName(rs.getString("sur_name"));
            return person;
        }
    }
}