package com.ayushmaanbhav.eventProcessor.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {SpringConstants.PACKAGE_EVENT_PROCESSOR})
public class EventProcessorSpringConfig {

}
