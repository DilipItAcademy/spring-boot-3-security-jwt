package com.dilip.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dilip.user.entity.UserRegister;
import com.dilip.user.repository.UsersRegisterRepository;
import com.dilip.user.request.UserRegisterRequest;


@Service
public class UsersRegisterService {
	
	@Autowired
	UsersRegisterRepository usersRegisterRepository;
	
	public String createUserAccount(UserRegisterRequest request) {
		
		UserRegister register = UserRegister.builder()  // Initilizing based on builder 
			.emailId(request.getEmailId())
			.password(request.getPassword())
			.name(request.getName())
			.mobile(request.getMobile())
			.build();  // Create instance with provided values 
		
		usersRegisterRepository.save(register);

		return "Registerded Successfully";
		
	}

}
