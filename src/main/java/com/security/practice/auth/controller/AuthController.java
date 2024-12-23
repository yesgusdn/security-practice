package com.security.practice.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.practice.auth.dto.LoginDto;
import com.security.practice.auth.dto.TokenDto;
import com.security.practice.auth.dto.UserCreateDto;
import com.security.practice.auth.service.JwtProviderService;
import com.security.practice.user.entity.UserEntity;
import com.security.practice.user.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final UserService userService;
	
	private final JwtProviderService jwtProviderService;
		
	private final AuthenticationManagerBuilder authenticationManagerBuilder;



	public AuthController(UserService userService, JwtProviderService jwtProviderService,
			AuthenticationManagerBuilder authenticationManagerBuilder) {
		this.userService = userService;
		this.jwtProviderService = jwtProviderService;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
	}

	@PostMapping("/signup")
	public ResponseEntity<?> createUser(@RequestBody UserCreateDto userCreateDto){
		try {
			UserEntity createdUser = userService.createUser(userCreateDto);
			return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
		
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
		
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtProviderService.generateToken(authentication);
		
		return new ResponseEntity<>(new TokenDto(jwt), HttpStatus.OK);
	}
}
