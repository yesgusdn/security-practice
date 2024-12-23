package com.security.practice.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.practice.auth.dto.UserCreateDto;
import com.security.practice.user.entity.UserEntity;
import com.security.practice.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;


	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}



	@Transactional
	public UserEntity createUser(UserCreateDto userCreateDto) {
		
		if(userRepository.existsByUsername(userCreateDto.getUsername())) {
			throw new RuntimeException("이미 존재하는 아이디입니다.");
		}
		
		UserEntity user = new UserEntity();
		user.setUsername(userCreateDto.getUsername());
		user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
		return userRepository.save(user);
	}
	
}
