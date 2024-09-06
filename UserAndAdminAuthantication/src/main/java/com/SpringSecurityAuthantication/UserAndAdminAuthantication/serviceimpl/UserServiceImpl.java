package com.SpringSecurityAuthantication.UserAndAdminAuthantication.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringSecurityAuthantication.UserAndAdminAuthantication.pojo.User;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.repository.UserRepository;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.service.UserService;

//@Service class as a Spring service for automatic detection and registration.
@Service
public class UserServiceImpl implements UserService {
	
	// Injects an instance of UserReposiotry for database operations
	@Autowired
	private UserRepository userReposiotry;

	// register a new user save method
	@Override
	public User registerUser(User user) {
		// TODO Auto-generated method stub
		return userReposiotry.save(user);
	}

	//loadUserByUsername: Retrieves a User object from the database by username.
	@Override
	public User loadUserByUsername(String userName) {
		// TODO Auto-generated method stub
		return userReposiotry.getUserByUserName(userName);
	}
	
	
}