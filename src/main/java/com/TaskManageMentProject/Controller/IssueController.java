package com.TaskManageMentProject.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManageMentProject.DTO.IssueDTO;
import com.TaskManageMentProject.Entity.IssueComent;
import com.TaskManageMentProject.Enum.IssueStatus;
import com.TaskManageMentProject.Service.IssueService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/Issues")
@RequiredArgsConstructor
public class IssueController {
	
	@Autowired
	private IssueService issueService;
	
	@PostMapping("/create")
	public ResponseEntity<IssueDTO>createIssue(@RequestBody IssueDTO dto){
		return ResponseEntity.ok(issueService.createIssue(dto));
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<IssueDTO>getIssueById(@PathVariable Long id){
		return ResponseEntity.ok(issueService.getById(id));
	}
	
	@PutMapping("/{id}/status")
	public ResponseEntity<IssueDTO>updateStatus(@PathVariable Long id,
			                                    @RequestParam IssueStatus status,
			                                    @RequestParam String performBy){
		return ResponseEntity.ok(issueService.updateIssueStatus(id, status, performBy));
	}
	@PostMapping("/{id}/comments")
	public ResponseEntity<IssueComent>addComments(@PathVariable Long id,
                                                  @RequestParam String author,
                                                  @RequestParam String body){
		return ResponseEntity.ok(issueService.addComment(id, author, body));
	}
	


    @GetMapping("/search")
    public ResponseEntity<List<IssueDTO>>search(@RequestParam Map<String,String>filter){
    	return ResponseEntity.ok(issueService.search(filter));
    }
}
