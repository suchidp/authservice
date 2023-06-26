package com.authservice.controller.request;

import com.authservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    public static UserResponse fromEntity(User entity) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(entity.getId());
        userResponse.setFirstName(entity.getEmail());
        userResponse.setLastName(entity.getLastName());
        userResponse.setEmail(entity.getEmail());
        return userResponse;
    }
}
