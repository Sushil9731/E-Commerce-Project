package com.example.service;


import java.util.List;

import com.example.exception.UserException;
import com.example.modal.User;

public interface UserService {
	
	public User findUserById(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public List<User> findAllUsers();

}

