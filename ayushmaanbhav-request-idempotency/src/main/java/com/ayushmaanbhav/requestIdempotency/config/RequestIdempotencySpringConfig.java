package com.ayushmaanbhav.requestIdempotency.config;

import com.ayushmaanbhav.requestIdempotency.constant.SpringConstants;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {SpringConstants.PACKAGE_REQUEST_IDEMPOTENCY})
@EntityScan(basePackages = {SpringConstants.PACKAGE_REQUEST_IDEMPOTENCY_ENITY})
public class RequestIdempotencySpringConfig {
}
