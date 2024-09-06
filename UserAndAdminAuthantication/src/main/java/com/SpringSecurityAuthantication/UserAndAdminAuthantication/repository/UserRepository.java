package com.SpringSecurityAuthantication.UserAndAdminAuthantication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SpringSecurityAuthantication.UserAndAdminAuthantication.pojo.User;


@Repository
//UserReposiotry Interface for interacting with User entities
public interface UserRepository extends JpaRepository<User, Long> {
	// @Query: Defines a custom query to fetch a User by userName
	@Query("SELECT u FROM User u WHERE u.userName = :userName")
	// getUserByUserName: Method to find a User entity based on the username.
	public User getUserByUserName(@Param("userName") String userName);
}