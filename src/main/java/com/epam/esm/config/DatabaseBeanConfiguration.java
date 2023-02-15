package com.epam.esm.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
/*
Database configuration class
 */

@Configuration()
@PropertySource("classpath:application-dev.properties")
public class DatabaseBeanConfiguration {

    @Value("${spring.datasource.url}")
    private String url;                                 //datasource url

    @Value("${spring.datasource.username}")
    private String username;                        //DBMS's username

    @Value("${spring.datasource.password}")
    private String password;                         //DBMS's password

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;              //Driver class for DBMS

    //Bean  for connecting with  database  for both profile  if we need can change profiles
    @Bean
    @Profile({"dev", "prod"})
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setJdbcUrl(url);
            dataSource.setUser(username);
            dataSource.setPassword(password);
            dataSource.setDriverClass(driverClassName);
        } catch (PropertyVetoException e) {
            e.getMessage();
        }
        return dataSource;
    }

    //Jdbc template  bean
    @Bean
    @Profile({"dev", "prod"})
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {

        return new JdbcTemplate(dataSource);
    }

}

