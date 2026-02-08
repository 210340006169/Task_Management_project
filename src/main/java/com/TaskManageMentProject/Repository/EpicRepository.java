package com.TaskManageMentProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManageMentProject.Entity.Epic;

@Repository
public interface EpicRepository extends JpaRepository<Epic,Long> {

}
