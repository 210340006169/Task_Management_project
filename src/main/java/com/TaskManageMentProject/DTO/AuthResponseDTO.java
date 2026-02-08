package com.TaskManageMentProject.DTO;


public class AuthResponseDTO {

	
	public String token;
	public String message;
	
	public AuthResponseDTO() {}
	public AuthResponseDTO(String token,String message) {
		this.token=token;
		this.message=message;
	}
	
	}
