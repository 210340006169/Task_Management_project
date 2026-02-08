package com.TaskManageMentProject.DTO;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LoginRequestDTO {

	public String userOfficialEmail;
	public String password;
}
