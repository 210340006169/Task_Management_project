package com.TaskManageMentProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManageMentProject.DTO.LoginRequestDTO;
import com.TaskManageMentProject.DTO.RegisterRequestDTO;
import com.TaskManageMentProject.Service.UserAuthenticateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserAuthenticateController {
	
	@Autowired
	private UserAuthenticateService authService;
	
	
	@PostMapping("/register")
	public ResponseEntity<String>register(@RequestBody RegisterRequestDTO dto){
		
				authService.register(dto);
				return ResponseEntity.ok("User register Successful");
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginRequestDTO dto){
		return ResponseEntity.ok(authService.login(dto));
	}

}








