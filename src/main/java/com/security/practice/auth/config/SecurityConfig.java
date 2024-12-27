package com.security.practice.auth.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.security.practice.auth.service.CustomUserDetailsService;
import com.security.practice.auth.service.JwtProviderService;

@Configuration
@EnableWebSecurity
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
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
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
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // 허용할 Origin
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP 메서드
        configuration.setAllowedHeaders(Arrays.asList("*")); // 허용할 Header
        configuration.setAllowCredentials(true); // 인증 정보 허용 (쿠키 전달)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 설정
        return source;	
	}

}
