package com.TaskManageMentProject.Entity;

import java.util.Set;

import com.TaskManageMentProject.Enum.NotificationEvent;

import jakarta.persistence.*;

@Entity
@Table(name="notification_scheme")
public class NotificationScheme {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Long projectId;
	
	@Enumerated(EnumType.STRING)
	private NotificationEvent eventType;
	
	@ElementCollection(fetch=FetchType.EAGER)
	private Set<String>receipient;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public NotificationEvent getEventType() {
		return eventType;
	}

	public void setEventType(NotificationEvent eventType) {
		this.eventType = eventType;
	}

	public Set<String> getReceipient() {
		return receipient;
	}

	public void setReceipient(Set<String> receipient) {
		this.receipient = receipient;
	}
	
	
	
	
	

}
