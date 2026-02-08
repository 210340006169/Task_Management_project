package com.TaskManageMentProject.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManageMentProject.Service.ReportingService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
	
	@Autowired
	private ReportingService reportingService;
	
	@GetMapping("/burnDown/{sprintId}")
	public ResponseEntity<Map<String,Object>>burnDown(@PathVariable Long sprintId){
		return ResponseEntity.ok(reportingService.burndown(sprintId));
		
	}
	@GetMapping("/velocity/{projectId}")
	public ResponseEntity<Map<String,Object>>veloccity(@RequestParam Long projectId){
		return ResponseEntity.ok(reportingService.velocity(projectId));
	}
	
	@GetMapping("/sprintReport/{sprintId}")
	public ResponseEntity<Map<String,Object>>sprintReport(@RequestParam Long sprintId){
		return ResponseEntity.ok(reportingService.sprintRepo(sprintId));
	}
	
	@GetMapping("/epicReport/{epicId}")
	public ResponseEntity<Map<String,Object>>epicReport(@RequestParam Long epicId){
		return ResponseEntity.ok(reportingService.epicReport(epicId));
	}
	@GetMapping("/workLoadReport/{sprintId}")
	public ResponseEntity<Map<String,Object>>workLoadReport(@RequestParam Long sprintId){
		return ResponseEntity.ok(reportingService.workLoad(sprintId));
	}
	@GetMapping("/cumulative-flow/{sprintId}")
	public ResponseEntity<Map<String,Object>>cumulativeFlow(@RequestParam Long sprintId){
		return ResponseEntity.ok(reportingService.cumulativeFlow(sprintId));
	}

}
