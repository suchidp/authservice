package com.authservice.service;

import com.authservice.model.User;
import com.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository repository;


    public User findUserById(int id) {
        Optional<User> user = repository.findById(id);
        return user.orElse(null);
    }

    public String addUser(User userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "user  added to system ";
    }


}




