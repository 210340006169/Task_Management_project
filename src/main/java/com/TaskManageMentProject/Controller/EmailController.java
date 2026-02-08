package com.TaskManageMentProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManageMentProject.Service.EmailService;

@RestController
@RequestMapping("/api/notification")
public class EmailController {
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/send")
	public ResponseEntity<String>sendEmail(@PathVariable String recepientEmail,@PathVariable String subject,@PathVariable String body){
		emailService.send(recepientEmail, subject, body);
		return ResponseEntity.ok("Email Sent Successfully");
	}

}
