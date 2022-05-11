package com.kaneki.springboot.bankapplication.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties("app.datasource")
    public DataSource appDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "appEntityManager")
    @ConfigurationProperties(prefix="spring.data.jpa.entity")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryApp(EntityManagerFactoryBuilder builder,
                                                                       @Qualifier(value = "appDataSource") DataSource appDataSource) {
        return builder
                .dataSource(appDataSource)
                .build();
    }
}