package com.foxminded.andreimarkov.warehouse.dao.impl;

import com.foxminded.andreimarkov.warehouse.dao.CompanyDAO;
import com.foxminded.andreimarkov.warehouse.model.Company;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class JdbcCompanyDAOImpl implements CompanyDAO {
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_FIND_COMPANY = "select id,name,balance,address,phone from schema.company where id = ?";
    private static final String SQL_DELETE_COMPANY = "delete from schema.company where id = ?";
    private static final String SQL_UPDATE_COMPANY = "update schema.company set name = ?, balance  = ?, address = ?, phone = ? where id = ?";
    private static final String SQL_GET_ALL = "select id,name,balance,address,phone from schema.company ORDER BY id ASC";
    private static final String SQL_INSERT_COMPANY = "insert into schema.company(name, balance, address, phone) values(?,?,?,?);";

    @Autowired
    public JdbcCompanyDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Company save(Company company) {
        log.debug("save company");
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
        log.debug("company {} saved", company.getName());
        return company;
    }

    @Override
    public List<Company> findAll() {
        log.debug("getting list of companies in findAll");
        return jdbcTemplate.query(SQL_GET_ALL, new BeanPropertyRowMapper<Company>(Company.class));
    }

    @Override
    public Optional<Company> getById(Long id) {
        try {
            log.debug("try to get company by id {}", id);
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_COMPANY,
                    new BeanPropertyRowMapper<Company>(Company.class), id));
        } catch (EmptyResultDataAccessException e) {
            log.error("get error: empty result, return optional.empty",e.getLocalizedMessage());
            return Optional.empty();
        }
    }

    @Override
    public Company update(Company company) {
        log.debug("update company");
        jdbcTemplate.update(SQL_UPDATE_COMPANY, company.getName(), company.getBalance(), company.getAddress(), company.getPhone(),company.getId());
        log.debug("company {} updated",company.getName());
        return company;
    }

    @Override
    public int delete(Long id) {
        log.debug("delete company by id {}",id);
        return jdbcTemplate.update(SQL_DELETE_COMPANY, id);
    }

}
