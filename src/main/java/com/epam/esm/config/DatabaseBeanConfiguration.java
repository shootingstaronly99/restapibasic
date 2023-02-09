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


@Configuration
@PropertySource("classpath:application-dev.properties")
public class DatabaseBeanConfiguration {


    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;


    @Bean
    @Profile({"dev", "prod"})
    public DataSource devDataSource() throws PropertyVetoException {
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


    @Bean
    @Profile({"dev", "prod"})
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {

        return new JdbcTemplate(dataSource);
    }

}

