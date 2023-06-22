package com.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenValidationResponse {
    private boolean isValidToken;
    private List<SimpleGrantedAuthority> authorities;
    private String role;
}
