package com.security.practice.auth.config;

import java.rmi.ServerException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.practice.auth.service.CustomUserDetailsService;
import com.security.practice.auth.service.JwtProviderService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private final JwtProviderService jwtProviderService;
	private final CustomUserDetailsService userDetailsService;
	
	public JwtAuthenticationFilter(JwtProviderService jwtProviderService, CustomUserDetailsService userDetailsService) {
		this.jwtProviderService = jwtProviderService;
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServerException, IOException{
		
		try {
			String token = jwtProviderService.extractToken(request);
			if(token != null && jwtProviderService.validateToken(token)) {
				
				String username = jwtProviderService.getUsernameFromToken(token);
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
			filterChain.doFilter(request, response);
			
		} catch (Exception e) {
			
		}
		
				
	}
	
}
