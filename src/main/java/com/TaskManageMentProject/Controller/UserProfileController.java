package com.TaskManageMentProject.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManageMentProject.DTO.UserProfileDTO;
import com.TaskManageMentProject.Service.UserProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/userProfile")
@RequiredArgsConstructor
public class UserProfileController {
	
	@Autowired
	private UserProfileService userProfileService;
	
	@PutMapping("/{email}")
	public ResponseEntity<UserProfileDTO>updateProfile(@RequestBody UserProfileDTO dto){
		return ResponseEntity.ok(userProfileService.updateUserProfile(dto));
	}
	@GetMapping("/all")
	public ResponseEntity<List<UserProfileDTO>>getAllUser(){
		return ResponseEntity.ok(userProfileService.getAllUserProfile());
	}
	@GetMapping("/{email")
	public ResponseEntity<UserProfileDTO>getUserByEmail(@PathVariable String userOfficialEmail){
		return ResponseEntity.ok(userProfileService.getProfileByEmail(userOfficialEmail));
	
   }
	@GetMapping("/{designation}")
	public ResponseEntity<List<UserProfileDTO>>getUserByDesignation(@PathVariable String designation){
		return ResponseEntity.ok(userProfileService.getUserByDesignation(designation));
	}
	@GetMapping("/{department}")
	public ResponseEntity<List<UserProfileDTO>>getUserByDepartment(@PathVariable String department){
		return ResponseEntity.ok(userProfileService.getUserByDepartment(department));
	}
	

}