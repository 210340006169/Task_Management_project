package com.TaskManageMentProject.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="work_flows")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkFlow {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=false,unique=true)
	private String workFlowName;
	
	@Column(length=5000)
	private String workFlowDescription;
	
	private LocalDateTime createdAt = LocalDateTime.now();
	
	@OneToMany(mappedBy="workflow",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<WorkFlowTransaction>Transaction=new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWorkFlowName() {
		return workFlowName;
	}

	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
	}

	public String getWorkFlowDescription() {
		return workFlowDescription;
	}

	public void setWorkFlowDescription(String workFlowDescription) {
		this.workFlowDescription = workFlowDescription;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<WorkFlowTransaction> getTransaction() {
		return Transaction;
	}

	public void setTransaction(List<WorkFlowTransaction> transaction) {
		Transaction = transaction;
	}
	
	
	

}
