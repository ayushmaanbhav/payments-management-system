package com.ayushmaanbhav.eventProcessor.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

import static com.ayushmaanbhav.eventProcessor.spring.SpringConstants.PACKAGE_EVENT_PROCESSOR_POJO;

@Configuration
public class TestDbConfig {

    @Bean
    //TODO define min, max connection values later
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:mem:test_ayushmaanbhav;DB_CLOSE_DELAY=-1;MODE=MySQL;NON_KEYWORDS=value");
        return dataSourceBuilder.build();
    }

    @Autowired
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource) {
        Properties props = new Properties();
        props.put("hibernate.dialect", "org.hibernate.spatial.dialect.h2geodb.GeoDBDialect");
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "create");
        props.put("hibernate.id.new_generator_mappings", "false");
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaProperties(props);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan(PACKAGE_EVENT_PROCESSOR_POJO);
        return entityManagerFactoryBean;
    }

    @Autowired
    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        JpaTransactionManager bean = new JpaTransactionManager();
        bean.setEntityManagerFactory(entityManagerFactoryBean.getObject());
        return bean;
    }

}
