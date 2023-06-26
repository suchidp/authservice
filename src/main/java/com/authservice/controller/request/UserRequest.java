package com.authservice.controller.request;

import com.authservice.model.Role;
import com.authservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserRequest {
    private Integer id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Set<Role> roles = new HashSet<>();


    public static User toEntity(UserRequest userRequest) {
        User entity = new User();
        entity.setId(userRequest.getId());
        entity.setFirstName(userRequest.getFirstName());
        entity.setLastName(userRequest.getLastName());
        entity.setPassword(userRequest.getPassword());
        entity.setEmail(userRequest.getEmail());
        entity.setRoles(userRequest.getRoles());
        System.out.println(entity);
        return entity;
    }
}
