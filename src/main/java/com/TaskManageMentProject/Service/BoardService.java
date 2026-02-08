package com.TaskManageMentProject.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TaskManageMentProject.Entity.Board;
import com.TaskManageMentProject.Entity.BoardCard;
import com.TaskManageMentProject.Entity.BoardColumn;
import com.TaskManageMentProject.Entity.Issue;
import com.TaskManageMentProject.Repository.BoardCardRepository;
import com.TaskManageMentProject.Repository.BoardColumnRepository;
import com.TaskManageMentProject.Repository.BoardRepository;
import com.TaskManageMentProject.Repository.IssueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor


public class BoardService {
	private static final Long cardId= null;
	@Autowired
	private BoardRepository boardRepo;
	@Autowired
	private BoardColumnRepository boardColumnRepo;
	@Autowired
	private BoardCardRepository boardCardRepo;
	@Autowired
	private IssueRepository issueRepo;
	
	
	public Board createBoard(Board board) {
		return boardRepo.save(board);
		
	}
	
	public Optional<Board>findByBoardId(Long id){
		return boardRepo.findById(id);
		
	}
	public List<Board> getByColumns(Long boardId){
		return boardColumnRepo.findByBoardIdOrderByPosition(boardId);
		
	}
	public List<BoardCard>getCardsForColumn(Long boardId,Long columnId){
		return boardCardRepo.findByboardIdAndColumnIdOrderByPosition(boardId, columnId);
		
	}
	@Transactional
	public BoardCard addIssueToBoard(Long boardId,Long columnId,Long issueId) {
		Issue issue = issueRepo.findById(issueId).orElseThrow(()->new RuntimeException("Issue not found"));
		boardCardRepo.findByIssueId(issueId).ifPresent(boardCardRepo::delete);
		
		BoardColumn column = boardColumnRepo.findById(columnId).orElseThrow(()->new RuntimeException("column not found"));
		if(column.getWipLimt()!=null && column.getWipLimt()>0) {
			long count = boardCardRepo.countByBoardIdAndColumnId(boardId, columnId);
			if(count >=column.getWipLimt()) {
				throw new RuntimeException("WIP limits reached for column:"+column.getName());
			}
		}
		List<BoardCard>exsiting = boardCardRepo.findByboardIdAndColumnIdOrderByPosition(boardId, columnId);
		int post = exsiting.size();
		BoardCard card = new BoardCard();
		card.setBoardId(boardId);
		card.setColumn(column);
		card.setIssueId(issueId);
		card.setPossition(post);
		card = boardCardRepo.save(card);
		if(column.getStatusKey()!=null) {
			issue.setStatus(Enum.valueOf(com.TaskManageMentProject.Enum.IssueStatus.class, column.getStatusKey()));
		}
		return card;
	}
	
	@Transactional
	public void moveCard(Long boardId,Long tocolumnId,int position,String performBy) {
		BoardCard card = boardCardRepo.getById(cardId);
		if(card == null) {
			throw new RuntimeException("Card not found");
		}
		
		BoardColumn from = card.getColumn();
		BoardColumn to = boardColumnRepo.findById(tocolumnId).orElseThrow(()->new RuntimeException("toStatus not found"));
		if(to == null) {
			throw new RuntimeException("Target not found");
		}
		
		if(to.getWipLimt()!=null && to.getWipLimt()>0) {
			long count = boardCardRepo.countByBoardIdAndColumnId(boardId, tocolumnId);
			if(!Objects.equals(from.getId(), to.getId()) && count>=to.getWipLimt()) {
				throw new RuntimeException("Wip limit Exceeded for column:"+ to.getWipLimt());
			}
		
		}
		
		List<BoardCard>fromList=boardCardRepo.findByboardIdAndColumnIdOrderByPosition(boardId, from.getId());
		for(BoardCard bc:fromList) {
			if(bc.getPossition()>card.getPossition()) {
				bc.setPossition(bc.getPossition()-1);
				boardCardRepo.save(bc);
			}
		}
		List<BoardCard>toList=boardCardRepo.findByboardIdAndColumnIdOrderByPosition(boardId, to.getId());
		for(BoardCard bc:toList) {
			if(bc.getPossition()>=position) {
				bc.setPossition(bc.getPossition()+1);
				boardCardRepo.save(bc);
				
			}
		}
		card.setColumn(to);
		card.setPossition(position);
		boardCardRepo.save(card);
		
		Issue issue = issueRepo.findById(card.getIssueId()).orElseThrow(()->new RuntimeException("Issue not found"));
		if(to.getStatusKey()!=null) {
			issue.setStatus(Enum.valueOf(com.TaskManageMentProject.Enum.IssueStatus.class, to.getStatusKey()));
			issueRepo.save(issue);
		}
			
		
		
	}
	
	@Transactional
	public void recordColumn(Long boardId,Long columnId,List<Long>orderCardIds) {
		int post = 0;
		for(Long cid:orderCardIds) {
			BoardCard card = boardCardRepo.findById(cid).orElseThrow(()->new RuntimeException("card notfound"));
			card.setPossition(post++);
			boardCardRepo.save(card);
		}
	}
	
	@Transactional
	public void completeSprint(Long SprintId) {
		
	}

	public void moveCard(Long id, Long toColumnId, Long cardId2, int toPosition, Object object) {
		
		
	}

	

	
	

}
