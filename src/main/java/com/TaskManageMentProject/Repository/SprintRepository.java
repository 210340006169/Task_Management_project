package com.TaskManageMentProject.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManageMentProject.Entity.Issue;
import com.TaskManageMentProject.Entity.Sprint;
import com.TaskManageMentProject.Enum.IssueStatus;
import com.TaskManageMentProject.Enum.SprintState;

@Repository
public interface SprintRepository extends JpaRepository<Sprint,Long> {
	
	Optional<Issue>findByIssueKey(String issueKey);

	Issue findBySprintId(Long sprintId);
	Issue findByIssueStatus(IssueStatus issueStatus);
	List<Sprint> findByProjectId(Long projectId);
	Sprint findByState(SprintState state);

}
