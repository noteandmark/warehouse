package com.foxminded.andreimarkov.warehouse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptException;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ComponentScan("com.foxminded.andreimarkov.warehouse")
@PropertySource("classpath:application.properties")
public class AppConfig {

    private final static String URL = "spring.datasource.url";
    private final static String USER = "spring.datasource.username";
    private final static String DRIVER = "spring.datasource.driver-class-name";
    private final static String PASSWORD = "spring.datasource.password";
    private final static String SCHEMA = "spring.datasource.schema";

    @Autowired
    Environment environment;

    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource postgresDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty(DRIVER));
        dataSource.setUrl(environment.getProperty(URL));
        dataSource.setUsername(environment.getProperty(USER));
        dataSource.setPassword(environment.getProperty(PASSWORD));
        prepareDatabase(dataSource);

        return dataSource;
    }

    public void prepareDatabase(DataSource dataSource) {
        Resource resource = new ClassPathResource(environment.getProperty(SCHEMA));
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        try {
            databasePopulator.populate(dataSource.getConnection());
        } catch (ScriptException | SQLException e) {
            e.printStackTrace();
        }
    }

}
