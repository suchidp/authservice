package com.authservice.controller;

import com.authservice.config.UserInfoUserDetailsService;
import com.authservice.exception.InvalidTokenException;
import com.authservice.model.*;
import com.authservice.repository.UserRepository;
import com.authservice.service.JwtService;
import com.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserInfoUserDetailsService userDetailsService;


    @PostMapping("/register")
    public String addNewUser(@RequestBody User user) {
        return service.addUser(user);
    }

    @PostMapping("/validate")
    public TokenValidationResponse validateToken(@RequestBody TokenValidationRequest tokenValidationRequest) throws InvalidTokenException {
        String token = tokenValidationRequest.getToken();
        final String username = jwtService.extractUsername(token);
        UserDetails userDetailsFromDB = userDetailsService.loadUserByUsername(username);
        boolean isValidToken = username.equals(userDetailsFromDB.getUsername()) && !jwtService.isTokenExpired(token);

        TokenValidationResponse response = new TokenValidationResponse();
        response.setValidToken(isValidToken);
        response.setName(username);
        response.setAuthorities((List<SimpleGrantedAuthority>) userDetailsFromDB.getAuthorities());
        System.out.println("response from validate " + response);
        return response;
    }

    @PostMapping("/token")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getName());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}

