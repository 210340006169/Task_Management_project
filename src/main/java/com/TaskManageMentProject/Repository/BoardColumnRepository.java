package com.TaskManageMentProject.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManageMentProject.Entity.Board;
import com.TaskManageMentProject.Entity.BoardColumn;

@Repository

public interface BoardColumnRepository extends JpaRepository<BoardColumn,Long> {
	
	List<Board> findByBoardIdOrderByPosition(Long boardId);
	
	

}
