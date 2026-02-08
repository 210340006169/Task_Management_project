package com.TaskManageMentProject.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManageMentProject.Entity.NotificationScheme;
import com.TaskManageMentProject.Enum.NotificationEvent;

@Repository
public interface NotificationSchemeRepository extends JpaRepository<NotificationScheme,Long> {
	Optional<NotificationScheme>findByProjectIdAndEventType(Long projectId,NotificationEvent eventType);

}
