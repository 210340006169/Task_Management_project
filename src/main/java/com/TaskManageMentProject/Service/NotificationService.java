package com.TaskManageMentProject.Service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskManageMentProject.Entity.Notification;
import com.TaskManageMentProject.Entity.NotificationScheme;
import com.TaskManageMentProject.Enum.NotificationEvent;
import com.TaskManageMentProject.Repository.NotificationRepository;
import com.TaskManageMentProject.Repository.NotificationSchemeRepository;

@Service
public class NotificationService {
	
	@Autowired
	private EmailService emailService;
	@Autowired
	private NotificationRepository notificationRepo;
	@Autowired
	private NotificationSchemeRepository notificationSchemeRepo;
	private String[] finalRecepients;
	
	public void notify(Long projectId,
			           NotificationEvent eventType,
			           Set<String>emails,
			           String subject,
			           String message,
			           Long entityId) {
		notificationSchemeRepo.findByProjectIdAndEventType(projectId, eventType).map(scheme->resolveRecepient(scheme,emails)).orElse(emails);
		
		for(String email:finalRecepients) {
			emailService.send(email, subject, message);
			Notification notification=new Notification();
			notification.setRecipientEmail(email);
			notification.setSubject(subject);
			notification.setBody(message);
			notification.setEvent(eventType);
			notification.setEntityId(entityId);
			notificationRepo.save(notification);
		}
	}

	private Set<String> resolveRecepient(NotificationScheme scheme, Set<String> fallBack) {
		
		return scheme.getReceipient();
	}
	
	
	

}
