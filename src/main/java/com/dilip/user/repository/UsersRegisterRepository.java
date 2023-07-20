package com.dilip.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dilip.user.entity.UserRegister;

@Repository
public interface UsersRegisterRepository extends JpaRepository<UserRegister, String>{

	Optional<UserRegister> findByEmailId(String emailId);

}
