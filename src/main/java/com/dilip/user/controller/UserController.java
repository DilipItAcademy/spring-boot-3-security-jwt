package com.dilip.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilip.jwt.token.JWTTokenHelper;
import com.dilip.user.request.UserLoginRequest;
import com.dilip.user.request.UserRegisterRequest;
import com.dilip.user.response.UserLoginResponse;
import com.dilip.user.response.UserRegisterResponse;
import com.dilip.user.service.UsersRegisterService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	Logger logger = LoggerFactory.getLogger(UserController.class); 
	
	@Autowired
	UsersRegisterService usersRegisterService;

	@Autowired
	JWTTokenHelper jwtTokenHelper;

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/hello")
	public String syaHello() {
		return "Welcome to Security";
	}

	@PostMapping("/register")
	public ResponseEntity<UserRegisterResponse> createUserAccount(@RequestBody UserRegisterRequest request) {
		
		request.setPassword(passwordEncoder.encode(request.getPassword()));
		
		String result = usersRegisterService.createUserAccount(request);
		return ResponseEntity.ok(new UserRegisterResponse(request.getEmailId(), result));
	}

	// 2. Login User
	@PostMapping("/login")
	public ResponseEntity<UserLoginResponse> loginUser(@RequestBody UserLoginRequest request) {
		this.doAuthenticate(request.getEmailId(), request.getPassword());
		String token = this.jwtTokenHelper.generateToken(request.getEmailId());
		return ResponseEntity.ok(new UserLoginResponse(token, request.getEmailId()));
	}

	private void doAuthenticate(String emailId, String password) {
		
		logger.info("Authentication of USer Credentils");
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(emailId, password);
		try {
			authenticationManager.authenticate(authentication);
		} catch (BadCredentialsException e) {
			throw new RuntimeException("Invalid UserName and Password");
		}
	}
	
	@GetMapping("/load/{userName}")
	public String getUserDetails(@PathVariable String userName) {
		
		return "USer Details are : "+userName;
	}

}
