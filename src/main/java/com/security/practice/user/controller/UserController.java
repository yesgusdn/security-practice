package com.security.practice.user.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.practice.user.entity.UserEntity;
import com.security.practice.user.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	private final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserInfo(@PathVariable("id") Long id){
		try {
			Optional<UserEntity> user = userRepository.findById(id);
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		} catch (DataAccessException  e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
		
}
