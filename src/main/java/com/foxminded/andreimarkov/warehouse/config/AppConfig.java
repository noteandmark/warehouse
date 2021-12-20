package com.foxminded.andreimarkov.warehouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.foxminded.andreimarkov.warehouse")
@PropertySource("classpath:application.properties")
public class AppConfig {



//    @Autowired
//    Environment environment;
//
    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        System.out.println("jdbc template run");
        return new JdbcTemplate(dataSource);
    }

//    @Bean(name = "dataSource")
//    public DataSource postgresDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(environment.getProperty(DRIVER));
//        dataSource.setUrl(environment.getProperty(URL));
//        dataSource.setUsername(environment.getProperty(USER));
//        dataSource.setPassword(environment.getProperty(PASSWORD));
//
//        return dataSource;
//    }
}
