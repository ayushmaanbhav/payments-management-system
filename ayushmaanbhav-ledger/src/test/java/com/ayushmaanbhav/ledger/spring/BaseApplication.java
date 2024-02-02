package com.ayushmaanbhav.ledger.spring;

import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static com.ayushmaanbhav.ledger.constant.SpringConstants.PACKAGE_LEDGER;
import static com.ayushmaanbhav.ledger.constant.SpringConstants.PACKAGE_LEDGER_ENTITY;

@Configuration
@ComponentScan(basePackages = {PACKAGE_LEDGER, PACKAGE_LEDGER_ENTITY})
@PropertySource(value = "classpath:config.properties", ignoreResourceNotFound = true)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, ErrorMvcAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class,
        ManagementWebSecurityAutoConfiguration.class})
public class BaseApplication {
}
