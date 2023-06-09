package com.authservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size(min = 4, max = 32)
    private String name;

    @NotEmpty
    @Size(min = 4, max = 32)
    private String email;

    @NotEmpty
    private String password;

    private String roles;
}