package com.TaskManageMentProject.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TaskManageMentProject.Enum.IssueStatus;


@FeignClient(name="issue-service",url="${issue-service.url}")
public interface IssueClient {
	@PutMapping("/api/issue/{id}/status")
	void updateStatus(@PathVariable Long id,
			          @RequestParam IssueStatus inProgress,
			          @RequestParam String performedBy);
	@PostMapping("/api/issue/{id}/comment")
	void addComment(@PathVariable Long id,
			        @RequestParam String author,
			        @RequestParam String body);
	void updateStatus(Long issueId, String string, String author);
	
	            

}
