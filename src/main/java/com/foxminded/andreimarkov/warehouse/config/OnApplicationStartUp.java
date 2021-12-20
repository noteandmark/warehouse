package com.foxminded.andreimarkov.warehouse.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@PropertySource(value= {"classpath:application.properties"})
public class OnApplicationStartUp {

    @Value("${spring.datasource.url}")
    private String URL;
    @Value("${spring.datasource.username}")
    private String USER;
    @Value("${spring.datasource.driver-class-name}")
    private String DRIVER;
    @Value("${spring.datasource.password}")
    private String PASSWORD;
    @Value("${spring.datasource.schema}")
    private String SCHEMA;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("creating DB");
        try {
            //noinspection SpringConfigurationProxyMethods
            this.datasource();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public DataSource datasource() throws PropertyVetoException {
        System.out.println("in datasource");
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
        dataSource.setSchema(SCHEMA);
        return dataSource;
    }

}