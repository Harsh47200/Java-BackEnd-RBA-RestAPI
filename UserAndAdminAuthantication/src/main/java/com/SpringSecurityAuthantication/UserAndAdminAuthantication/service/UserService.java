package com.SpringSecurityAuthantication.UserAndAdminAuthantication.service;

import org.springframework.http.ResponseEntity;

import com.SpringSecurityAuthantication.UserAndAdminAuthantication.pojo.User;

public interface UserService {
	// new user register
		public User registerUser(User user);

		// Retrieves a User object from the database by username.
		public User loadUserByUsername(String userName);
	}
