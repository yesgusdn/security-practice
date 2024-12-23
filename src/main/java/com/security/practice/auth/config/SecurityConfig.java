package com.security.practice.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.security.practice.auth.service.CustomUserDetailsService;
import com.security.practice.auth.service.JwtProviderService;

@Configuration
public class SecurityConfig {
	
	private CustomUserDetailsService userDetailsService;
	private JwtProviderService jwtProviderService;
	
	public SecurityConfig(CustomUserDetailsService userDetailsService, JwtProviderService jwtProviderService) {
		this.userDetailsService = userDetailsService;
		this.jwtProviderService = jwtProviderService;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
		    .csrf(csrfConfig -> csrfConfig.disable())
		    .authorizeHttpRequests(auth -> auth
		    		    .requestMatchers("/api/auth/**", "/h2-console/**").permitAll()
		                .anyRequest().authenticated())
			.formLogin(formLogin -> 
					formLogin.disable()
			)
			.logout(logout -> logout.permitAll())
            .addFilterBefore(new JwtAuthenticationFilter(jwtProviderService, userDetailsService), UsernamePasswordAuthenticationFilter.class);
		                                           
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder();
	}
	

}
