package com.forkcast.service;

import org.springframework.http.ResponseEntity;

public interface HealthService {

    ResponseEntity<String> checkHealth();
}
