package com.TaskManageMentProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TaskManageMentProject.DTO.AuthResponseDTO;
import com.TaskManageMentProject.DTO.LoginRequestDTO;
import com.TaskManageMentProject.DTO.RegisterRequestDTO;
import com.TaskManageMentProject.Entity.UserAuthenticate;
import com.TaskManageMentProject.Enum.Role;
import com.TaskManageMentProject.Repository.UserAuthenticationRepository;
import com.TaskManageMentProject.Security.JWTTokenUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAuthenticateService {
	
	
	@Autowired
	private UserAuthenticationRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncode;
	
	@Autowired
	private JWTTokenUtil jwtUtil;
	
	
	public void register(RegisterRequestDTO req) {
		
		
		if(userRepo.findByUserOfficialEmail(req.userOfficiallEmail).isPresent()) {
			throw new RuntimeException("Profile alredt Exists:" + req.userOfficiallEmail);
		}
		UserAuthenticate user = new UserAuthenticate();
		
		user.setUserName(req.userName);
		user.setUserOfficialEmail(req.userOfficiallEmail);
		user.setPassword(passwordEncode.encode(req.password));
		user.setRole(req.role);
		
		userRepo.save(user);
	}
	
	
	public AuthResponseDTO login (LoginRequestDTO dto) {
		
		UserAuthenticate user = userRepo.findByUserOfficialEmail(dto.userOfficialEmail)
				.orElseThrow(()-> new RuntimeException("User not found"));
		
		
		if(!passwordEncode.matches(dto.password, user.getPassword())) {
		throw new RuntimeException("Invalid Credential");
	}
	
	String token = (jwtUtil.generateToken(user));
	return new AuthResponseDTO(token,"Token got generate");
	

}
}



