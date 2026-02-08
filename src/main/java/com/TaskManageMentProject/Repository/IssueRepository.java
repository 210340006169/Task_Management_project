package com.TaskManageMentProject.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManageMentProject.Entity.Issue;
import com.TaskManageMentProject.Enum.IssueStatus;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    Optional<Issue> findByIssueKey(String issueKey);

    List<Issue> findByAssigneeEmail(String assigneeEmail);

    List<Issue> findBySprintId(Long sprintId);

    List<Issue> findByStatus(IssueStatus status); // fixed name from issueStatus to status

    // Now projectId exists in Issue, so this works
    List<Issue> findByProjectIdAndSprintIdIsNullOrderByBackLogPosition(Long projectId);

    List<Issue> findByEpicId(Long epicId);

	List<Issue> findBackLogByProjectId(Long projectId);
	
}
