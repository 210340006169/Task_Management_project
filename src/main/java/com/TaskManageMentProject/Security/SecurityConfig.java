package com.TaskManageMentProject.Security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
@Configuration
public class SecurityConfig {
	@Autowired
	private JWTTokenUtil jwtUtil;
	
	@Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    	return authenticationConfiguration.getAuthenticationManager();
    	
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    	return http.csrf().disable().authorizeHttpRequests(auth-> auth
    			.requestMatchers("/api/auth ")
    			.permitAll()
    			.anyRequest()
    			.authenticated())
    	        .build();
    }

    @Bean
    
     public PasswordEncoder passwordEncoder1() {
    	return new BCryptPasswordEncoder();
    }

	
    
	
}
