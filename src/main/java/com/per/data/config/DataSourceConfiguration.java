package com.per.data.config;

import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DataSourceConfiguration {

    @Bean
    @LiquibaseDataSource
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public DataSource mySqlDataSource() {
        log.info("configuring mysql connection");
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.h2")
    public DataSource h2DataSource() {
        log.info("configuring h2 connection");
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.liquibase")
    public LiquibaseProperties liquibaseProperties(){
        log.info("gathering liquibase properties");
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase liquibase(){
        LiquibaseProperties properties = liquibaseProperties();
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setDataSource(mySqlDataSource());
        springLiquibase.setChangeLog(properties.getChangeLog());
        springLiquibase.setDatabaseChangeLogTable(properties.getDatabaseChangeLogTable());
        springLiquibase.setDatabaseChangeLogLockTable(properties.getDatabaseChangeLogLockTable());
        springLiquibase.setContexts(properties.getContexts());
        springLiquibase.setDefaultSchema(properties.getDefaultSchema());
        springLiquibase.setShouldRun(true);
        return springLiquibase;
    }
}
