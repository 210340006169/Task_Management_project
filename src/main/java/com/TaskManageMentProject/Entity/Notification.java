package com.TaskManageMentProject.Entity;

import java.time.LocalDateTime;

import com.TaskManageMentProject.Enum.NotificationEvent;

import jakarta.persistence.*;

@Entity
@Table(name="notification")
public class Notification {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String recipientEmail;
	private String subject;
	
	@Column(columnDefinition="TEXT",length=10000)
	private String body;
	
	private NotificationEvent event;
	private Long entityId;
	private LocalDateTime createdAt=LocalDateTime.now();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRecipientEmail() {
		return recipientEmail;
	}
	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public NotificationEvent getEvent() {
		return event;
	}
	public void setEvent(NotificationEvent event) {
		this.event = event;
	}
	public Long getEntityId() {
		return entityId;
	}
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
	

}
