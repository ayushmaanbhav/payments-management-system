package com.ayushmaanbhav.ledger.config;

import com.ayushmaanbhav.ledger.constant.SpringConstants;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {SpringConstants.PACKAGE_LEDGER})
@EntityScan(basePackages = {SpringConstants.PACKAGE_LEDGER_ENTITY})
public class LedgerSpringConfig {
}
