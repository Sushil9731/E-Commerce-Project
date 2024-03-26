package com.example.service;

import com.example.exception.UserException;
import com.example.model.User;

// write all the services for all the users
public interface UserService {

	public User findUserById(Long userid) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
}
