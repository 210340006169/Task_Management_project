package com.TaskManageMentProject.DTO;

import com.TaskManageMentProject.Enum.Role;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {

	public String userName;
	public String userOfficiallEmail;
	public String password;
	public Role role;
	
}
