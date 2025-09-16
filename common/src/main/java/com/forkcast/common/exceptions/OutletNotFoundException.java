package com.forkcast.common.exceptions;

public class OutletNotFoundException extends RuntimeException {
    public OutletNotFoundException(Long id) {
        super("Outlet not found with id: " + id);
    }

    public OutletNotFoundException(String email) {
        super("Outlet not found for owner with email: " + email);
    }
}
