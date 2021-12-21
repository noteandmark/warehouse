package com.foxminded.andreimarkov.warehouse.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@PropertySource(value= {"classpath:application.properties"})
@ConditionalOnProperty(
        value="database.enabled",
        matchIfMissing = false)
public class DataBaseLoader {

    @Value("${spring.datasource.url}")
    private String URL;
    @Value("${spring.datasource.username}")
    private String USER;
    @Value("${spring.datasource.driver-class-name}")
    private String DRIVER;
    @Value("${spring.datasource.password}")
    private String PASSWORD;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("creating DB"); // later delete this
        try {
            this.datasource();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        System.out.println("jdbc template run"); // later delete this
        return new JdbcTemplate(dataSource);
    }

    public DataSource datasource() throws PropertyVetoException {
        System.out.println("in datasource"); // later delete this
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
        // schema init
        Resource initSchema = new ClassPathResource("schema.sql");
        Resource initData = new ClassPathResource("data.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, initData);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        return dataSource;
    }

}