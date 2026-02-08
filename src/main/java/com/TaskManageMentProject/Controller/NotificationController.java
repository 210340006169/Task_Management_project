package com.TaskManageMentProject.Controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManageMentProject.Entity.Notification;
import com.TaskManageMentProject.Enum.NotificationEvent;
import com.TaskManageMentProject.Service.NotificationService;

@RestController
@RequestMapping("api/notification")
public class NotificationController {
	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/notify")
	public ResponseEntity<String>sendNotification(@RequestParam Long projectId,
			                                      @RequestParam NotificationEvent eventType,
			                                      @RequestParam Long entityId,
			                                      @RequestParam Set<String>emails,
			                                      @RequestParam String subject,
			                                      @RequestParam String message){
		notificationService.notify(projectId,eventType,emails,subject,message,entityId);
		return ResponseEntity.ok("Notification sent successful");
	}

}
