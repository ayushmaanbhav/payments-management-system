package com.ayushmaanbhav.payment.config;

import com.ayushmaanbhav.gatewayProvider.config.GatewayProviderSpringConfig;
import com.ayushmaanbhav.payment.constant.SpringConstants;
import com.ayushmaanbhav.requestIdempotency.config.RequestIdempotencySpringConfig;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@ComponentScan(basePackages = {SpringConstants.PACKAGE_PAYMENTS})
@EntityScan(basePackages = {SpringConstants.PACKAGE_PAYMENTS_ENITY})
@Import({GatewayProviderSpringConfig.class, RequestIdempotencySpringConfig.class})
public class PaymentSpringConfig {
}
