package com.foxminded.andreimarkov.warehouse.listener;

import com.foxminded.andreimarkov.warehouse.exceptions.ServiceException;
import com.foxminded.andreimarkov.warehouse.service.impl.WarehouseServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.sql.DataSource;

@Component
@ConditionalOnProperty(
        value = "development.enabled",
        matchIfMissing = false)
public class JdbcDataSourceListener implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    private static final String SQL_FILE = "startedData.sql";
    private final DataSource dataSource;
    private ApplicationContext applicationContext;
    private final WarehouseServiceImpl warehouseService;

    @Autowired
    public JdbcDataSourceListener(DataSource dataSource, WarehouseServiceImpl warehouseService) {
        this.warehouseService = warehouseService;
        Assert.notNull(dataSource, "DataSource must not be null!");
        this.dataSource = dataSource;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!event.getApplicationContext().equals(applicationContext)) {
            return;
        }
        loadStartUpData();
//        try {
//            int count = warehouseService.findAll().size();
//        } catch (ServiceException e) {
//            loadStartUpData();
//        }
    }

    private void loadStartUpData() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setScripts(new Resource[]{new ClassPathResource(SQL_FILE)});
        DatabasePopulatorUtils.execute(populator, dataSource);
    }
}