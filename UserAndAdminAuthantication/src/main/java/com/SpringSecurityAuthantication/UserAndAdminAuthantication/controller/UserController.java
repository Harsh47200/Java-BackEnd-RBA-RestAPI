package com.SpringSecurityAuthantication.UserAndAdminAuthantication.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.SpringSecurityAuthantication.UserAndAdminAuthantication.jwt.JWTHelper;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.pojo.User;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.response.ApiResponse;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTHelper jwtHelper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/auth/login")
    public ResponseEntity<ApiResponse<String>> loginAuth(@RequestBody User user) {
        try {
            if (isBlank(user.getUserName()) || isBlank(user.getPassword())) {
                return new ResponseEntity<>(new ApiResponse<>(400, "Username and password cannot be blank", null), HttpStatus.BAD_REQUEST);
            }

            doAuthenticate(user.getUserName(), user.getPassword());

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String token = jwtHelper.generateToken(userDetails);

            return new ResponseEntity<>(new ApiResponse<>(200, "Login successful", token), HttpStatus.OK);

        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new ApiResponse<>(401, "Invalid Username or Password", null), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(500, "An error occurred: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/registerUser")
    public ResponseEntity<ApiResponse<User>> registerUser(@RequestBody User user) {
        if (user == null || isBlank(user.getUserName()) || isBlank(user.getPassword()) || "Select".equals(user.getRole())) {
            return new ResponseEntity<>(new ApiResponse<>(400, "Invalid input data", null), HttpStatus.BAD_REQUEST);
        }

        if (userService.loadUserByUsername(user.getUserName()) != null) {
            return new ResponseEntity<>(new ApiResponse<>(400, "Username already exists", null), HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.registerUser(user);

        return new ResponseEntity<>(new ApiResponse<>(201, "User registered successfully", user), HttpStatus.CREATED);
    }

    
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void doAuthenticate(String userName, String password) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userName, password)
        );
    }
}
