package com.TaskManageMentProject.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManageMentProject.Entity.BoardCard;

@Repository

public interface BoardCardRepository extends JpaRepository<BoardCard,Long> {
	
	List<BoardCard>findByboardIdAndColumnIdOrderByPosition(Long boardId,Long columnId);
	Long countByBoardIdAndColumnId(Long boardId,Long columnId);
	Optional<BoardCard>findByIssueId(Long issueId);
	

}
