package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.config.JwtProvider;
import com.example.exception.UserException;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.request.LoginRequest;
import com.example.response.AuthResponse;
import com.example.service.CustomUserServiceImplementation;

// the use of rest controller using this api endpoint we can acccess it on frontend
@RestController
@RequestMapping("/auth") // what we write in this method all methods start with endpoint auth
public class AuthController {
// start signup method
	
	// making intense of userRepository.
	private UserRepository userRepository;
	// token generation instace
		private JwtProvider jwtProvider;
		private PasswordEncoder passwordEncoder;
		private CustomUserServiceImplementation customUserService;
		
	public AuthController(UserRepository userRepository,CustomUserServiceImplementation customUserService,PasswordEncoder passwordEncoder,JwtProvider jwtProvider) {
		this.userRepository=userRepository;
		this.customUserService=customUserService;
		this.passwordEncoder=passwordEncoder;
		this.jwtProvider=jwtProvider;
	}
	
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse>createUserHnadler(@RequestBody User user)throws UserException{

		

		String email=user.getEmail();
		String password=user.getPassword();
		String firstName=user.getFirstName();
		String lastName=user.getLastName();
		
		User isEmailExist=userRepository.findByEmail(email);
		if(isEmailExist!=null)
		{
			throw new UserException("Email is already used with Another account");
		}
		
		
		
		User createdUser=new User();
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFirstName(firstName);
		createdUser.setLastName(lastName);
		
		User savedUser=userRepository.save(createdUser);// for getting saved user
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);		
		
		String token=jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse=new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("Signup Success");
		
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
		
		
	}

	
	@PostMapping("/signin") // cause we send data in requestBody
	//login method
	public ResponseEntity<AuthResponse>loginUserHandler(@RequestBody LoginRequest loginRequest)
	{
		
		String username=loginRequest.getEmail();
		String password=loginRequest.getPassword();
		
		Authentication authentication=Authenticate(username,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		//generate token and return auth response
		
		String token=jwtProvider.generateToken(authentication);
		AuthResponse authResponse=new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("Signin Success");
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
	}

// if password match then authenticate otherwise throw exception
	private Authentication Authenticate(String username, String password) {
		UserDetails userDetails=customUserService.loadUserByUsername(username);
		if(userDetails==null)
		{
			throw new BadCredentialsException("Invalid User Name...");// user name is not present in Database
		}
		if(!passwordEncoder.matches(password,userDetails.getPassword()))
		{
			throw new BadCredentialsException("Invalid Password...");
		}
		
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}//complete the authentication method
}
