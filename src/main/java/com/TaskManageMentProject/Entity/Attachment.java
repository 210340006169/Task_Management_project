package com.TaskManageMentProject.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="attachments")
public class Attachment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Long issueId;
	private String fileName;
	private String contentType;
	private String fileSize;
	private String storagePath;
	private String cloudId;
	private String uploadedBy;
	
	
	public Long getId() {
		return id;
	}


	public Long getIssueId() {
		return issueId;
	}


	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getContentType() {
		return contentType;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	public String getFileSize() {
		return fileSize;
	}


	public void setFileSize(String fileSize){
		this.fileSize = fileSize;
	}


	public String getStoragePath() {
		return storagePath;
	}


	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}


	public String getCloudId() {
		return cloudId;
	}


	public void setCloudId(String cloudId) {
		this.cloudId = cloudId;
	}


	public String getUploadedBy() {
		return uploadedBy;
	}


	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}


	public void setId(Long id) {
		this.id = id;
	}


	
	
	
}