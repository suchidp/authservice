package com.authservice.controller;

import com.authservice.config.UserInfoUserDetailsService;
import com.authservice.controller.request.UserRequest;
import com.authservice.controller.request.UserResponse;
import com.authservice.exception.AuthtenticationException;
import com.authservice.exception.InvalidTokenException;
import com.authservice.model.*;
import com.authservice.repository.UserRepository;
import com.authservice.service.JwtService;
import com.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> addNewUser(@RequestBody UserRequest userRequest) {
        if (userService.existsByEmail(userRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        } else {
            User user = userRequest.toEntity(userRequest);
            return new ResponseEntity<>(UserResponse.fromEntity(userService.addUser(user)), HttpStatus.CREATED);
        }
    }

    @PostMapping("/validate")
    public TokenValidationResponse validateToken(@RequestBody TokenValidationRequest tokenValidationRequest) throws InvalidTokenException {
        String token = tokenValidationRequest.getToken();
        final String email = jwtService.extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        boolean isValidToken = email.equals(userDetails.getUsername()) && !jwtService.isTokenExpired(token);
        User user = userService.getUserByUsername(email);
        String role = user.getRoles().toString();
        TokenValidationResponse response = new TokenValidationResponse();
        response.setRole(role);
        response.setValidToken(isValidToken);
        response.setAuthorities(userDetails.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList()));
        return response;
    }

    @PostMapping("/token")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws AuthtenticationException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            User user = userService.getUserByUsername(authRequest.getEmail());
            if (!user.isAccountNonExpired() || !user.isAccountNonLocked() || !user.isCredentialsNonExpired() || !user.isEnabled()) {
                return ResponseEntity.badRequest().body("Account is not valid");
            }
            return ResponseEntity.status(HttpStatus.OK).body(jwtService.generateToken(authRequest.getEmail()));
        } else {
            throw new AuthtenticationException("invalid user request !");
        }
    }
}