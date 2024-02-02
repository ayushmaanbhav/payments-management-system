package com.ayushmaanbhav.eventProcessor.spring;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Data
@Service
@Valid
public class EventProcessorApplicationProperties {

    private final int logicAppMaxRetries;

    @Autowired
    public EventProcessorApplicationProperties(
            @Value("${azure.logicapp.events.maxretries}") int logicAppMaxRetries
    ) {
        this.logicAppMaxRetries = logicAppMaxRetries;
    }

}
