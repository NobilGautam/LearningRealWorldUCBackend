package com.forkcast.dao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.forkcast.common.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    
    @JsonProperty("token")
    private String token;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("role")
    private Role role;
    
    @JsonProperty("message")
    private String message;
}
