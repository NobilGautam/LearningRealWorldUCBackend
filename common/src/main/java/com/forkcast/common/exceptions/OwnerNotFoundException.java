package com.forkcast.common.exceptions;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException(String email) {
        super("Owner not found with email: " + email);
    }
}

