package com.TaskManageMentProject.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskManageMentProject.Entity.Issue;
import com.TaskManageMentProject.Entity.Sprint;
import com.TaskManageMentProject.Enum.IssueType;
import com.TaskManageMentProject.Enum.SprintState;
import com.TaskManageMentProject.Repository.IssueRepository;
import com.TaskManageMentProject.Repository.SprintRepository;

import jakarta.transaction.Transactional;

@Service

public class BackLogService {
	private static final int Issue = 0;
	private static final int List = 0;
	private static final Map<String, Object> subTasksMap = null;


	@Autowired
	private IssueRepository issueRepo;
	
	
	@Autowired
	private SprintRepository sprintRepo;
	
	public List<Issue>getBackLog(Long projectId){
		return issueRepo.findBackLogByProjectId(projectId);
		
	}
	
	@Transactional
	public void recordBackLog(Long projectId, List<Long>orderIssueId) {
		
		int pos =0;
		for(Long issueId:orderIssueId) {
			Issue issue = issueRepo.findById(issueId).orElseThrow(()->new RuntimeException("Issue not found"));
			issue.setBackLogPosition(pos++);
			issueRepo.save(issue);
		}
		
	}
	@Transactional
	public Issue addIssueToSprint(Long issueId,Long sprintId) {
		
		Issue issue = issueRepo.findById(issueId).orElseThrow(()-> new RuntimeException("issue not found"));
		Sprint sprint = sprintRepo.findById(sprintId).orElseThrow(()->new RuntimeException("sprint not found"));
		
		SprintState sprintState = sprint.getState();
		if(sprintState != SprintState.PLANNED && sprintState != SprintState.ACTIVE) {
			throw new RuntimeException("can not add issue to sprint in state:"+sprintState);
		}
		issue.setSprintId(sprintId);
		issue.setBackLogPosition(null);
		return issueRepo.save(issue);
		
		
	}
	
	public Map<String,Object>getBackLogHierarchy(Long projectId){
		List<Issue> backLog = getBackLog(projectId);
		
		Map<String,Object> epicMap = new LinkedHashMap<>();
		for(Issue i:backLog) {
			   if(i.getIssueType()==IssueType.EPICS) {
				Map<String,Object>data=new LinkedHashMap<>();
				data.put("epic", i);
				data.put("stories", new ArrayList<Issue>());
				data.put("subtasks", new HashMap<Long,List<Issue>>());
				
			}
		}
		for(Issue i:backLog) {
			if(i.getIssueType()==IssueType.STORIES && i.getEpicId()!=null) {
				Map<String,Object> epicData = (Map<String, Object>) epicMap.get(i.getEpicId());
				if(epicData != null) {
					List<Issue> stories=(List<Issue>)epicData.get("stories");
					stories.add(i);
				}
			}
		}
		
		for(Issue i:backLog) {
			if(i.getIssueType()==IssueType.SUBTASKS && i.getSourceIssueId()!=null) {
				Long sourceIssueId = i.getSourceIssueId();
				for(Object epicData:epicMap.values()) {
					List<Issue> stories=(List<Issue>)((Map<String,Object>) epicData).get("stories");
					for(Issue story:stories) {
						if(story.getId().equals(sourceIssueId)) {
							Map<Long,List<Issue>>subTaskAbsent=(Map<Long,List<Issue>>)((Map<String, Object>) epicData).get("subTasks");
							break;
						}
					}
					
				}
				
				
				
			}
		}
		return Collections.singletonMap("epics", epicMap.values());
		
	}
	
	
	
	
	

}