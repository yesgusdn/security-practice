package com.security.practice.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto {
	
	private String username;
	private String password;
}
