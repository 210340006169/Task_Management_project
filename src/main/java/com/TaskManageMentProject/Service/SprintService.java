package com.TaskManageMentProject.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskManageMentProject.Entity.Issue;
import com.TaskManageMentProject.Entity.Sprint;
import com.TaskManageMentProject.Enum.IssueStatus;
import com.TaskManageMentProject.Enum.SprintState;
import com.TaskManageMentProject.Repository.IssueRepository;
import com.TaskManageMentProject.Repository.SprintRepository;

import jakarta.transaction.Transactional;

@Service

public class SprintService {
	@Autowired
	private SprintRepository sprintRepo;
	
	@Autowired
	private IssueRepository issueRepo;
	
	public Sprint createSprint(Sprint sprint) {
		sprint.setState(SprintState.PLANNED);
		return sprintRepo.save(sprint);
	}
	
	@Transactional
	public Issue assignIssueToSprint(Long sprintId,Long issueId) {
		Sprint sprint = sprintRepo.findById(sprintId).orElseThrow(()->new RuntimeException("Sprint not found"));
		Issue issue = issueRepo.findById(issueId).orElseThrow(()->new RuntimeException("Issue not found"));
		
		if(sprint.getState()!=SprintState.COMPLETE) {
			throw new RuntimeException("Can not addissue to completed sprint");
		}
		issue.setSprintId(sprintId);
		return issueRepo.save(issue);
		
	}
	
	@Transactional
	public Sprint startSprint(Long sprintId) {
		Sprint sprint= sprintRepo.findById(sprintId).orElseThrow(()->new RuntimeException("Sprint not found"));
		if(sprint.getState()!=SprintState.PLANNED) {
			throw new RuntimeException("Only planned sprint can be start");
			
		}
		if(sprint.getStartDate()==null) {
			sprint.setStartDate(LocalDateTime.now());
		}
		
		return sprintRepo.save(sprint);
	}
	
	@Transactional
	public Sprint closetrSprint(Long sprintId) {
		Sprint sprint = sprintRepo.findById(sprintId).orElseThrow(()->new RuntimeException("Sprint not found"));
		sprint.setState(SprintState.COMPLETE);
		if(sprint.getEndDate()==null) {
			sprint.setEndDate(LocalDateTime.now());
		}
		List<Issue>issues = issueRepo.findBySprintId(sprintId);
		for(Issue issue:issues) {
			if(!issue.getStatus().name().equals(IssueStatus.DONE)) {
				issue.setSprintId(null);
				issue.setBackLogPosition(0);
				issueRepo.save(issue);
				
			}
			
		}
		return sprintRepo.save(sprint);
		
	}
	public Map<String,Object>getBurnDownDate(Long sprintId){
		Sprint sprint = sprintRepo.findById(sprintId).orElseThrow(()->new RuntimeException("Sprint not found"));
		
		LocalDateTime start=sprint.getStartDate();
		LocalDateTime end=sprint.getEndDate()!=null?
				      sprint.getEndDate():LocalDateTime.now();
		
		List<Issue>issue=issueRepo.findBySprintId(sprintId);
		int totalTask=issue.size();
		
		Map<String,Object> burndown = new LinkedHashMap<>();
		LocalDateTime cursor = start;
		while(cursor.isAfter(end)) {
			long completed=issue.stream().filter(i->"DONE".equals(i.getStatus().name())).count();
			burndown.put(cursor.toString(), totalTask-(int)completed);
			cursor=cursor.plusDays(1);
		}
		
		Map<String,Object>response=new HashMap<>();
		response.put("sprintId", sprintId);
		response.put("startDate", start);
		response.put("endDate", end);
		response.put("burnDown", burndown);
		return response;
		}

}
