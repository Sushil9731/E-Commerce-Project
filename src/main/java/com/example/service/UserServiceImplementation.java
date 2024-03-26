package com.example.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.config.JwtProvider;
import com.example.exception.UserException;
import com.example.model.User;
import com.example.repository.UserRepository;


@Service
public class UserServiceImplementation implements UserService {

	// making instance and constructor
	private UserRepository userRepository;
	
	private JwtProvider jwtProvider;
	
	public UserServiceImplementation(UserRepository userRepository,JwtProvider jwtProvider) {
		this.userRepository=userRepository;
		this.jwtProvider=jwtProvider;
	}
	
	
	
	@Override
	public User findUserById(Long userid) throws UserException {
		Optional<User>user=userRepository.findById(userid);
		if(user.isPresent())
		{
			return user.get();
		}
		throw new UserException("User not found with ID-"+userid);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		
		String email=jwtProvider.getEmailFromToken(jwt);
		User user=userRepository.findByEmail(email);
		
		if(user==null)
		{
			throw new UserException("User not found with email-"+email);
		}
		
		return user;
	}

}
