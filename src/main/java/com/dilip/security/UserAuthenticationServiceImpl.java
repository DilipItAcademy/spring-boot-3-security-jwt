package com.dilip.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dilip.user.entity.UserRegister;
import com.dilip.user.repository.UsersRegisterRepository;

@Service
public class UserAuthenticationServiceImpl implements UserDetailsService{

	Logger logger = LoggerFactory.getLogger(UserAuthenticationServiceImpl.class);
	
	@Autowired
	UsersRegisterRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
		logger.info("Fetching UserDetails");
		UserRegister user =  repository.findByEmailId(emailId).orElseThrow(() -> new UsernameNotFoundException("Invalid User Name"));
		return user;
	}

}
