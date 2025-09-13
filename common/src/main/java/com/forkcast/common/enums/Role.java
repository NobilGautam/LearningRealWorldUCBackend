package com.forkcast.common.enums;

/**
 * Enum representing user roles in the system
 */
public enum Role {
    ADMIN("Admin"),
    OUTLET_MANAGER("Outlet Manager");
    
    private final String displayName;
    
    Role(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
