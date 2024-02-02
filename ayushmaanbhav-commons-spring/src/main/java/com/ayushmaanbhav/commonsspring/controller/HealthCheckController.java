package com.ayushmaanbhav.commonsspring.controller;

import com.ayushmaanbhav.commonsspring.db.dao.HealthCheckDao;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    private final HealthCheckDao healthCheckDao;

    @GetMapping("/health")
    public void healthCheck() {
        healthCheckDao.getHealthCheck();
    }

}
