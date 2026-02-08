package com.TaskManageMentProject.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Comment;

import com.TaskManageMentProject.Client.UserClient;
import com.TaskManageMentProject.DTO.IssueDTO;
import com.TaskManageMentProject.Entity.Issue;
import com.TaskManageMentProject.Entity.IssueComent;
import com.TaskManageMentProject.Enum.IssuePriority;
import com.TaskManageMentProject.Enum.IssueStatus;
import com.TaskManageMentProject.Enum.Role;
import com.TaskManageMentProject.Repository.EpicRepository;
import com.TaskManageMentProject.Repository.IssueCommentRepository;
import com.TaskManageMentProject.Repository.IssueRepository;
import com.TaskManageMentProject.Repository.SprintRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {
	
	@Autowired
	private IssueRepository issueRepo;
	
	@Autowired
	private IssueCommentRepository issuecommentRepo;
	
	@Autowired
	private SprintRepository sprintRepo;
	
	@Autowired
	private EpicRepository epicRepo;
	
	@Autowired
	private WorkFlowService workFlowService;
	
	@Autowired
	private UserClient userClient;
	
	private String generatedKey(Long id) {
		return"PROJECT-"+id;
	}
	
	@Transactional
	
	
	public IssueDTO createIssue(IssueDTO  dto) {
		Issue issue = new Issue();
	
		
		issue.setIssueTitle(dto.getIssueTitle());
		issue.setIssueType(dto.getIssueType());
		issue.setIssueKey("PROJECT"+issue.getId());
		issue.setAssigneeEmail(dto.getAssigneeEmail());
		issue.setRepoterEmail(dto.getReporterEmail());
		issue.setCreatedAt(dto.getCreatedAt());
		issue.setDescription(dto.getDescription());
		issue.setPriority(IssuePriority.LOW);
		issue.setStatus(IssueStatus.OPEN);
		issue.setUpdateAt(dto.getUpdateAt());
		issue.setDueDate(dto.getDueDate());
		
		if(dto.getEpicId()!=null) {
			epicRepo.findById(dto.getEpicId()).orElseThrow(()->new RuntimeException("Epic not found"));
			issue.setEpicId(dto.getEpicId());
		
		
		
	}
		if(dto.getSprintId()!=null) {
			
			issue.setSprintId(dto.getSprintId());
		}
		issueRepo.save(issue);
		return toDTO(issue);
	
	}
	public IssueDTO getById(Long id) {
		Issue issue = issueRepo.findById(id).orElseThrow(()->new RuntimeException("Issue not found"));
		return toDTO(issue);
	

	}
	@Transactional
	public IssueDTO updateIssueStatus(Long id,IssueStatus status,String performBy) {
		Issue issue = issueRepo.findById(id).orElseThrow(()->new RuntimeException("Issue not found"));
		//IssueStatus newStatus;
		//try {
			//newStatus = IssueStatus.valueOf(String.valueOf(status).toUpperCase().trim());
		//}
		//catch(Exception e) {
			//throw new RuntimeException("Invalid Status"+status);
			
		//}
		
		String from = issue.getStatus().name();
		String to = status.name();
		
		Long workFlowId=issue.getWorkFlowId();
		if(workFlowId==null) {
			throw new RuntimeException("WorkFlow not assigned to issue");
		}
		Set<Role>userRole=userClient.getRole(performBy);
		
		boolean allowed=workFlowService.isTransactionAllowed(workFlowId, from, to, userRole);
		if(!allowed) {
			throw new RuntimeException("User"+performBy+"is not allowed to move issue from"+from+"->"+to);
		}
		issue.setStatus(status);
		issueRepo.save(issue);
		IssueComent coment = new IssueComent();
		coment.setIssueId(id);
	    coment.setAuthorEmail(performBy);
	    coment.setBody("Status changed from:"+from+"->"+to);
	    issuecommentRepo.save(coment);
	    return toDTO(issue);
	}
	@Transactional
     public IssueComent addComment(Long issueId,String authorEmail,String body) {
		
		issueRepo.findById(issueId).orElseThrow(()-> new RuntimeException("Issue not found"));
		
		IssueComent comment = new IssueComent();
		comment.setIssueId(issueId);
		comment.setAuthorEmail(authorEmail);
		comment.setBody(body);
		
		return issuecommentRepo.save(comment);
		
	}
	
	
	
	
	public List<IssueDTO>search(Map<String,String>filter){
		
		if(filter.containsKey("assignee")) {
			
			return issueRepo.findByAssigneeEmail(filter.get("assignee")).stream().map(this::toDTO).collect(Collectors.toList());
			
		}
		if(filter.containsKey("status")) {
			String statusStr = filter.get("status");
			IssueStatus status;
			try {
				status = IssueStatus.valueOf(statusStr.toUpperCase().trim());
				
			}
			catch(Exception e) {
				throw new RuntimeException("Invalid Status:"+statusStr+"Allowed"+Arrays.toString(IssueStatus.values()));
			}
			return issueRepo.findByStatus(status).stream().map(this::toDTO).collect(Collectors.toList());
		}
   
	
	
	if(filter.containsKey("sprint")){
		Long sprintId = Long.valueOf(filter.get("sprint"));
		return issueRepo.findBySprintId(sprintId).stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	
		return issueRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	
	private IssueDTO toDTO(Issue issue) {
		IssueDTO dto=new IssueDTO();
		dto.setIssueTitle(issue.getIssueTitle());
		dto.setDescription(issue.getDescription());
		dto.setCreatedAt(issue.getCreatedAt());
		dto.setIssueKey(issue.getIssueKey());
		dto.setIssuePriority(issue.getPriority());
		dto.setIssueStatus(issue.getStatus());
		dto.setIssueType(issue.getIssueType());
		
		return dto;
		
	}
}