package com.authservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@NoArgsConstructor
public class TokenValidationResponse {
   private boolean isValidToken;
    private String name;
    private String password;
    private List<SimpleGrantedAuthority> authorities;


}
