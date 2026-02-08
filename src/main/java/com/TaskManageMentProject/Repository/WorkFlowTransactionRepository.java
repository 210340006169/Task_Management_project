package com.TaskManageMentProject.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManageMentProject.Entity.WorkFlowTransaction;

@Repository
public interface WorkFlowTransactionRepository extends JpaRepository<WorkFlowTransaction,Long> {
	List<WorkFlowTransaction>findByWorkFlowId(Long workflowId);
	List<WorkFlowTransaction>findByWorkFlowFromStatus(Long workflowId,String fromStatus);
	

}
