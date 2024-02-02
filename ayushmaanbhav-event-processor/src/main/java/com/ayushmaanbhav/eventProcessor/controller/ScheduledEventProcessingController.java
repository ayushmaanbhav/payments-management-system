package com.ayushmaanbhav.eventProcessor.controller;

import com.ayushmaanbhav.commons.response.EventProcessorResponse;
import com.ayushmaanbhav.eventProcessor.dto.EventProcessorDtoApi;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("event")
@RequiredArgsConstructor
public class ScheduledEventProcessingController {

    private final EventProcessorDtoApi eventProcessorDtoApi;

    @PreAuthorize("@permissionService.verify('ADMIN')")
    @PostMapping("/process")
    public EventProcessorResponse processEvent() {
        return eventProcessorDtoApi.processEvent();
    }

}
