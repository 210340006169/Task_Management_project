package com.TaskManageMentProject.Client;

import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.TaskManageMentProject.Enum.Role;

@FeignClient(name="user-service")
public interface UserClient {
	@GetMapping("/api/users/{email}/roles")
	Set<Role>getRole(@PathVariable  String userOfficialEmail);

}
