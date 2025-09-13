package com.forkcast.service.impl;

import com.forkcast.service.HealthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class HealthServiceImpl implements HealthService {

    private static final Logger log = LoggerFactory.getLogger(HealthServiceImpl.class);
    
    @Override
    public ResponseEntity<String> checkHealth() {
        log.debug("Health check requested");
        
        return new ResponseEntity<>("Server is healthy", HttpStatus.OK);
    }
}
