package com.authservice.controller;

import com.authservice.config.UserInfoUserDetailsService;
import com.authservice.exception.AuthtenticationException;
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
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

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
        if (userService.existsByEmail(user.getEmail())) {
            return "Email already exists";
        } else {
            return userService.addUser(user);
        }
    }

    @PostMapping("/validate")
    public TokenValidationResponse validateToken(@RequestBody TokenValidationRequest tokenValidationRequest) throws InvalidTokenException {
        String token = tokenValidationRequest.getToken();
        final String username = jwtService.extractUsername(token);
        UserDetails userDetails  = userDetailsService.loadUserByUsername(username);
        boolean isValidToken = username.equals(userDetails .getUsername()) && !jwtService.isTokenExpired(token);
        User user = userService.getUserByUsername(username);
        TokenValidationResponse response = new TokenValidationResponse();
        response.setUser(user);
        response.setValidToken(isValidToken);
        response.setAuthorities((List<SimpleGrantedAuthority>) userDetails .getAuthorities());
        return response;
    }

    @PostMapping("/token")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest)  throws AuthtenticationException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getName());
        } else {
            throw new AuthtenticationException("invalid Authentication request !");
        }
    }
}

