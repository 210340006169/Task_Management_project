package com.TaskManageMentProject.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.TaskManageMentProject.Entity.WorkFlow;
import com.TaskManageMentProject.Entity.WorkFlowTransaction;
import com.TaskManageMentProject.Service.WorkFlowService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WorkFlowController {
	
	@Autowired
	private WorkFlowService workflowService;
	
	@PostMapping("/create")
	public ResponseEntity<WorkFlow>create(@RequestBody WorkFlow wf){
		return ResponseEntity.ok(workflowService.createWorkFlow(wf));
		
	}
	@GetMapping("/list")
	public ResponseEntity<List<WorkFlow>>allList(){
		return ResponseEntity.ok(workflowService.liatAll());
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<WorkFlow>get(@PathVariable Long id){
		return ResponseEntity.ok(workflowService.getById(id));
		
	}
	@GetMapping("/{WorkFlowName}")
	public ResponseEntity<Optional<WorkFlow>>getByName(@PathVariable String workflowName){
		return ResponseEntity.ok(workflowService.findByWorkFlowName(workflowName));
	}
	@PutMapping("/update/{id}")
	public ResponseEntity<WorkFlow>update(@PathVariable Long id,@RequestBody WorkFlow wf){
		return ResponseEntity.ok(workflowService.updateWorkFlowStatus(id, wf));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String>delete(@PathVariable Long id){
		workflowService.delateWorkFlow(id);
		return ResponseEntity.ok("Deleted");
		
		
		
	}
	@GetMapping("/{id}/transaction/{from}")
	public ResponseEntity<List<WorkFlowTransaction>>allowed(@PathVariable Long id, @PathVariable String fromStatus){
		return ResponseEntity.ok(workflowService.allowedTransactions(id, fromStatus));
		
	}

}
