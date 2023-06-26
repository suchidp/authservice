package com.authservice.service;

import com.authservice.exception.RoleNotFoundException;
import com.authservice.exception.UserNotFoundException;
import com.authservice.model.Role;
import com.authservice.model.User;
import com.authservice.repository.RoleRepository;
import com.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    public User addUser(User userInfo) {
        Set<Role> savedRoles = new HashSet<>();
        for (Role role : userInfo.getRoles()) {
            boolean roleExists = roleRepository.existsByName(role.getName());
            Role existingRole = roleRepository.findByName(role.getName());
            if (!roleExists) {
                System.out.println("role not exist");
                throw new RoleNotFoundException("Role does not exist");
            }
            savedRoles.add(existingRole);
        }
        userInfo.setRoles(savedRoles);
        repository.save(userInfo);
        return userInfo;
    }

    public User getUserByUsername(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}






