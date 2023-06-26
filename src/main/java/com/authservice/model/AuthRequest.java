package com.authservice.model;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthRequest {
    @NotEmpty
    private String email ;
    @NotEmpty
    private String password;
}
