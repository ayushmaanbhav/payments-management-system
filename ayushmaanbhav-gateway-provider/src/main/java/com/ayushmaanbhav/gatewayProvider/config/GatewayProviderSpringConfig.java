package com.ayushmaanbhav.gatewayProvider.config;

import com.ayushmaanbhav.gatewayProvider.constant.SpringConstants;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {SpringConstants.PACKAGE_GATEWAY_PROVIDER, SpringConstants.PACKAGE_GATEWAY_PROVIDER_CLIENTS})
@EntityScan(basePackages = {SpringConstants.PACKAGE_GATEWAY_PROVIDER_ENTITY})
public class GatewayProviderSpringConfig {
}
