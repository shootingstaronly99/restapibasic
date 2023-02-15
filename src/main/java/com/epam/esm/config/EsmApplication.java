package com.epam.esm.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*
Main class for scanning and configuring
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.epam.esm")
public class EsmApplication implements WebMvcConfigurer {


}
