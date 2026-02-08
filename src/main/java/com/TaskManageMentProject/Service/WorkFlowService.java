package com.TaskManageMentProject.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskManageMentProject.Entity.WorkFlow;
import com.TaskManageMentProject.Entity.WorkFlowTransaction;
import com.TaskManageMentProject.Enum.Role;
import com.TaskManageMentProject.Repository.WorkFlowRepository;
import com.TaskManageMentProject.Repository.WorkFlowTransactionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkFlowService {
	
	@Autowired
	private WorkFlowRepository workflowRepo;
	@Autowired
	private WorkFlowTransactionRepository WorkFlowTransRepo;
	@Transactional
	public WorkFlow createWorkFlow(WorkFlow wf) {
		for(WorkFlowTransaction trans:wf.getTransaction())trans.setWorkflow(wf);
		
		return workflowRepo.save(wf);
	}
	public List<WorkFlow>liatAll(){
		return workflowRepo.findAll();
		}
	public WorkFlow getById(Long id) {
		return workflowRepo.getById(id);
	}
	public Optional<WorkFlow>findByWorkFlowName(String workFlowName){
		return workflowRepo.findByWorkFlowName(workFlowName);
		
	}
	
	@Transactional
	public WorkFlow updateWorkFlowStatus(Long id,WorkFlow updated) {
		WorkFlow wf = new WorkFlow();
		wf.getId();
		wf.setWorkFlowName(updated.getWorkFlowName());
		wf.setWorkFlowDescription(updated.getWorkFlowDescription());
		wf.getTransaction().clear();
		
		if(updated.getTransaction()!=null) {
			for(WorkFlowTransaction trasn:updated.getTransaction()) {
				trasn.setWorkflow(wf);
			wf.getTransaction().add(trasn);
			}
		}
		return workflowRepo.save(wf);
	}
	
	public void delateWorkFlow(Long id) {
		workflowRepo.deleteById(id);
	}
	public List<WorkFlowTransaction>allowedTransactions(Long workflowId,String fromStatus){
		return WorkFlowTransRepo.findByWorkFlowFromStatus(workflowId, fromStatus);
	}
	public boolean isTransactionAllowed(Long WorkFlowId,String fromStatus,String toStatus,Set<Role>userRole) {
		List<WorkFlowTransaction>list=WorkFlowTransRepo.findByWorkFlowFromStatus(WorkFlowId,fromStatus);
		for(WorkFlowTransaction trans :list){
			if(trans.getToStatus().equals(toStatus)) {
				String allowed=trans.getAllowedRole();
				if(allowed == null ||allowed.isEmpty()) return true;
				Set<String>allowedSet = Arrays.stream(allowed.split(",")).map(String::trim).collect((Collectors.toSet()));
				for(Role r : userRole) if(allowedSet.contains(r))return true;
				
				
			}
			
		}
		return false;
		
	}

	
}
								
	


