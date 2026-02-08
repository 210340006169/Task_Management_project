package com.TaskManageMentProject.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="board_columns", indexes= {@Index(columnList="board_id,position")})
public class BoardColumn {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board-id")
	private Board board;
	
	private String name;
	private String statusKey;
	private Integer position;
	private Integer wipLimt;
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatusKey() {
		return statusKey;
	}
	public void setStatusKey(String statusKey) {
		this.statusKey = statusKey;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public Integer getWipLimt() {
		return wipLimt;
	}
	public void setWipLimt(Integer wipLimt) {
		this.wipLimt = wipLimt;
	}
	
	

}