package com.TaskManageMentProject.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TaskManageMentProject.Entity.Issue;
import com.TaskManageMentProject.Service.BackLogService;

@RestController
@RequestMapping("/api/backlog")
public class BackLogController {

    @Autowired
    private BackLogService backLogService;

    // 🔹 Get backlog
    @GetMapping("/{projectId}")
    public ResponseEntity<List<Issue>> getBackLog(
            @PathVariable Long projectId) {

        return ResponseEntity.ok(backLogService.getBackLog(projectId));
    }

    // 🔹 Reorder backlog
    @PutMapping("/reorder/{projectId}")
    public ResponseEntity<String> reorderBackLog(
            @PathVariable Long projectId,
            @RequestBody List<Long> issueIds) {

        backLogService.recordBackLog(projectId, issueIds);
        return ResponseEntity.ok("Backlog reordered successfully");
    }

    // 🔹 Add issue to sprint
    @PutMapping("/add-to-sprint")
    public ResponseEntity<Issue> addIssueToSprint(
            @RequestParam Long issueId,
            @RequestParam Long sprintId) {

        return ResponseEntity.ok(
                backLogService.addIssueToSprint(issueId, sprintId));
    }

    // 🔹 Get hierarchy (Epic → Story → Subtask)
    @GetMapping("/hierarchy/{projectId}")
    public ResponseEntity<Map<String, Object>> getBackLogHierarchy(
            @PathVariable Long projectId) {

        return ResponseEntity.ok(
                backLogService.getBackLogHierarchy(projectId));
    }
}
