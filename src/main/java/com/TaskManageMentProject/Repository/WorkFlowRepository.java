package com.TaskManageMentProject.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManageMentProject.Entity.WorkFlow;

@Repository

public interface WorkFlowRepository extends JpaRepository<WorkFlow,Long>{
	Optional<WorkFlow>findByTransactionName(String transactionName);
	Optional<WorkFlow>findByWorkFlowName(String WorkFlowName);
	


}
