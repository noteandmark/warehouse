package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.dao.CompanyDAO;
import com.foxminded.andreimarkov.warehouse.model.Company;
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
public class JdbcCompanyDAOImpl implements CompanyDAO {
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_FIND_COMPANY = "select name,balance,address,phone from schema.company where id = ?";
    private static final String SQL_DELETE_COMPANY = "delete from schema.company where id = ?";
    private static final String SQL_UPDATE_COMPANY = "update schema.company set name = ?, balance  = ?, address = ?, phone = ? where id = ?";
    private static final String SQL_GET_ALL = "select name,balance,address,phone from schema.company";
    private static final String SQL_INSERT_COMPANY = "insert into schema.company(name, balance, address, phone) values(?,?,?,?);";

    @Autowired
    public JdbcCompanyDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Company create(Company company) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(SQL_INSERT_COMPANY, new String[] {"id"});
                    ps.setString(1,  company.getName());
                    ps.setInt(2,  company.getBalance());
                    ps.setString(3,  company.getAddress());
                    ps.setString(4,  company.getPhone());
                    return ps;
                },
                keyHolder);
        company.setId(keyHolder.getKey().longValue());
        return company;
    }

    @Override
    public List<Company> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL, new BeanPropertyRowMapper<Company>(Company.class));
    }

    @Override
    public Optional<Company> getById(Long id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_COMPANY,
                    new BeanPropertyRowMapper<Company>(Company.class), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Company update(Company company) {
        jdbcTemplate.update(SQL_UPDATE_COMPANY, company.getName(), company.getBalance(), company.getAddress(), company.getPhone(),company.getId());
        return company;
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update(SQL_DELETE_COMPANY, id);
    }

}
