package com.TaskManageMentProject.Entity;

import java.time.LocalDateTime;

import com.TaskManageMentProject.Enum.Role;

import jakarta.persistence.*;

import lombok.*;
@Entity
@Table(name="user_Auth")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder


public class UserAuthenticate {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=false)
	private String userName;
	@Column(unique=true,nullable=false)
	private String userOfficialEmail;
	@Column(nullable=false)
	private String password;
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Role role;
	private boolean active=true;
	private LocalDateTime createdAt=LocalDateTime.now();
	
	
	//public UserAuthentication() {} //NoArgsConstructor
	
	//public UserAuthentication(Long id,String userName,String userOfficialEmail,String password,Role role) {
	//   this.id=id;
	//   this.userName=userName;
	//   this.userOfficialEmail=userOfficialEmail;
	//   this.Rolr role;
	//   }
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserOfficialEmail() {
		return userOfficialEmail;
	}
	public void setUserOfficialEmail(String userOfficialEmail) {
		this.userOfficialEmail = userOfficialEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
	

}
