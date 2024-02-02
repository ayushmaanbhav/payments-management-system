package com.ayushmaanbhav.eventProcessor.spring;

import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static com.ayushmaanbhav.eventProcessor.spring.SpringConstants.PACKAGE_EVENT_PROCESSOR;
import static com.ayushmaanbhav.eventProcessor.spring.SpringConstants.PACKAGE_EVENT_PROCESSOR_POJO;

@Configuration
@ComponentScan(basePackages = {PACKAGE_EVENT_PROCESSOR, PACKAGE_EVENT_PROCESSOR_POJO})
@PropertySource(value = "classpath:config.properties", ignoreResourceNotFound = true)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, ErrorMvcAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class,
        ManagementWebSecurityAutoConfiguration.class})
public class BaseApplication {


}
