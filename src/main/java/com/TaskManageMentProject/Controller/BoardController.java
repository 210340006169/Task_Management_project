package com.TaskManageMentProject.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManageMentProject.Entity.Board;
import com.TaskManageMentProject.Entity.BoardCard;
import com.TaskManageMentProject.Entity.BoardColumn;
import com.TaskManageMentProject.Service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/create")
	public ResponseEntity<Board>createBoard(@RequestBody Board board){
		return ResponseEntity.ok(boardService.createBoard(board));
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Board>>getBoardById(@PathVariable Long id){
		return ResponseEntity.ok(boardService.findByBoardId(id));
	}
	@GetMapping("/{id}/column")
	public ResponseEntity<List<Board>>getColumns(@PathVariable Long boardId){
		return ResponseEntity.ok(boardService.getByColumns(boardId));
		
	}
	@PostMapping("/{id}/columns")
	public ResponseEntity<Board>addColumns(@PathVariable Long id,@RequestBody BoardColumn boardcolumn){
		boardcolumn.setBoard(boardService.findByBoardId(id).orElseThrow(()->new RuntimeException("column not found")));
		return ResponseEntity.ok(boardService.createBoard(boardcolumn.getBoard()));
	}
	@PostMapping("/{id}/cards")
	public ResponseEntity<BoardCard>addCards(@PathVariable Long id,@RequestBody Map<String,Object>body){
		Long columnId = Long.valueOf(String.valueOf(body.get("columnId")));
		Long issueId=Long.valueOf(String.valueOf(body.get("issueId")));
		return ResponseEntity.ok(boardService.addIssueToBoard(id, columnId, issueId));
	}
	
	@PostMapping("/{id}/cards/{CardId}/move")
	public ResponseEntity<String>moveCards(@PathVariable Long id,
			                               @PathVariable Long cardId,
			                               @RequestBody Map<String,Object>body,
			                               @RequestHeader(value="Ex_User_Email",required=false)String user){
		Long toColumnId=Long.valueOf(String.valueOf(body.get("toColumnId")));
		int toPosition=Integer.valueOf(String.valueOf(body.getOrDefault("toPosition", "0")));
		boardService.moveCard(id,toColumnId,cardId,toPosition,user==null?"System":user);
		return ResponseEntity.ok("moved");
	}
	@PostMapping("/{id}/columns/{columnId}/record")
	public ResponseEntity<String>recordColumns(@PathVariable Long id,
			                                   @PathVariable Long columnId,
			                                   @RequestBody List<Long>orderCardIds){
		boardService.recordColumn(id, columnId, orderCardIds);
		return ResponseEntity.ok("Recorded");
	}
	@PostMapping("/sprint/{sprintId}/start")
	public ResponseEntity<String>startSprint(@PathVariable Long sprintId){
		boardService.completeSprint(sprintId);
		return ResponseEntity.ok("Sprint completed");
	}
	
	

}