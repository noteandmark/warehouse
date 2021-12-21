package com.foxminded.andreimarkov.warehouse.dao;

import com.foxminded.andreimarkov.warehouse.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcPersonDAOImpl implements AbstractDAO<T>{

    private JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_PERSON = "select * from person where id = ?";
    private final String SQL_DELETE_PERSON = "delete from person where id = ?";
    private final String SQL_UPDATE_PERSON = "update person set first_name = ?, sur_name = ?, balance  = ?, address = ?, phone = ? where id = ?";
    private final String SQL_GET_ALL = "select * from person";
    private final String SQL_INSERT_PERSON = "insert into person(id, first_name, sur_name, balance, address, phone) values(?,?,?,?,?,?)";

    //    @Autowired
    public JdbcPersonDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Person> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL, new PersonRowMapper());
    }


    public Person getById(Long id) {
        return jdbcTemplate.queryForObject(SQL_FIND_PERSON, new Object[]{id}, new PersonRowMapper());
    }


    @Override
    public T create(T t) {
        return null;
    }

    @Override
    public Optional<T> getById(int id) {
        return Optional.empty();
    }

    public List<Person> getAllPersons() {
        return null;
    }

    @Override
    public T updatePerson(T t) {
        return null;
    }

    @Override
    public void deletePerson(T t) {

    }


    public boolean deletePerson(Person person) {
        return false;
    }


    public boolean updatePerson(Person person) {
        return false;
    }


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