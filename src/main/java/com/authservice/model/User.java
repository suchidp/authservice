package com.authservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size(min = 4, max = 32)
    private String firstName;

    @NotEmpty
    @Size(min = 4, max = 32)
    private String lastName;

    @NotEmpty
    private String password;

    @Column(unique = true)
    @NotEmpty
    @Size(min = 4, max = 32)
    private String email;

    @ManyToMany(cascade = {
            CascadeType.MERGE
    })
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    private boolean accountNonExpired = Boolean.TRUE;

    private boolean accountNonLocked = Boolean.TRUE;

    private boolean credentialsNonExpired = Boolean.TRUE;

    private boolean enabled = Boolean.TRUE;

}

