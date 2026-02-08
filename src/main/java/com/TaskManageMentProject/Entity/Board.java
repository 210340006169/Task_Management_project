package com.TaskManageMentProject.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.TaskManageMentProject.Enum.BoardType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="boards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String projectkey;
	@Enumerated(EnumType.STRING)
	@Column(name = "board_type")
	private BoardType boardType;
	
	private LocalDateTime createdAt = LocalDateTime.now();
	
	@OneToMany(mappedBy="board",cascade=CascadeType.ALL,orphanRemoval=true)
	@OrderBy("position")
	private List<BoardColumn> columns = new ArrayList<>(); 
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectkey() {
		return projectkey;
	}

	public void setProjectkey(String projectkey) {
		this.projectkey = projectkey;
	}

	public BoardType getBoardType() {
		return boardType;
	}

	public void setBoardType(BoardType boardType) {
		this.boardType = boardType;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
	

}