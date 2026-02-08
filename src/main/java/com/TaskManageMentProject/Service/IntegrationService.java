package com.TaskManageMentProject.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskManageMentProject.Client.IssueClient;
import com.TaskManageMentProject.Enum.IssueStatus;

@Service
public class IntegrationService {
	@Autowired
	private IssueClient issueClient;
	
	public void commitMessage(String msg,String author) {
		String regex = "([A-Z]+-\\d)";
		Matcher matcher = Pattern.compile(regex).matcher(msg);
		if(matcher.find()) {
			Long issueId = Long.parseLong(matcher.group(1).split("-")[1]);
			issueClient.updateStatus(issueId,"DONE",author);
			issueClient.addComment(issueId,author,"Close via comment:"+msg);
		}
	}
	
	public void handlePullRequest(String title,String author) {
		String regex = "([A-Z]+-\\d)";
		Matcher matcher = Pattern.compile(regex).matcher(title);
		
		if(matcher.find()) {
			Long issueId = Long.parseLong(matcher.group(1).split("-")[1]);
			issueClient.updateStatus(issueId, IssueStatus.IN_PROGRESS, author);
			issueClient.addComment(issueId, author, "Pull Request opened:"+title);
			
			
		}
	}

}
