package com.TaskManageMentProject.Security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.TaskManageMentProject.Entity.Attachment;
import com.TaskManageMentProject.Repository.AttachmentRepository;
import com.cloudinary.Cloudinary;

import jakarta.validation.ValidationException;

@Service("securityAttachmentService")

public class AttachmentService {
	@Autowired
	@Qualifier("securityAttachmentService")
	private AttachmentService attachmentService;


	
	@Autowired
	private AttachmentRepository attachmentRepo;
	
	@Autowired
	private Cloudinary cloudinary;
	
	
	public Attachment uploadFile(Long issue,MultipartFile file,String uploadBy) throws FileUploadException, ValidationException {
		
		validateFile(file);
		try {
			
			Map<String,Object> uploadOptions = new HashMap<>();
			uploadOptions.put("Resouce-Type", "auto");
			
			Map uploadResults = cloudinary.uploader().upload(file.getBytes(),uploadOptions );
			
			
			Attachment attach =new Attachment();
			attach.setIssueId(issue);
			attach.setFileName(file.getOriginalFilename());
			attach.setContentType(file.getContentType());
			attach.setStoragePath(uploadResults.get("source_url").toString());
			attach.setCloudId(uploadResults.get("Clouud_Id").toString());
			attach.setUploadedBy(uploadBy);
					
			return attachmentRepo.save(attach);
			
		} catch (Exception e) {
			throw new FileUploadException("File not found",e) ;
		}
		
	}
	
	
	
	
	public List<Attachment>getfileByIssueId(Long issueId){
		return attachmentRepo.findByIssueId(issueId);
	}
	
	public Attachment getFileById(Long id) {
		return attachmentRepo.findById(id).orElseThrow(()-> new RuntimeException("Attachment not found"));
	}
	
	public void deleteFile(Long id) throws FileUploadException {
		Attachment att = attachmentRepo.findById(id).orElseThrow(()-> new RuntimeException("Attachment not found"));
		
		try {
			
			Map<String,Object> options = new HashMap<>();
			
			options.put("Resource_Type", "auto");
			cloudinary.uploader().destroy(att.getCloudId(), options);
			attachmentRepo.delete(att);
			
			
		} catch (Exception e) {
			throw new FileUploadException("Delete failed",e);
		}
	}
	
	private void validateFile(MultipartFile file) throws ValidationException {
		
		if(file.isEmpty()) {
			throw new ValidationException("file can not be empty");
		}
		
		long Max = 5*1024*1024;
		
		if(file.getSize()>Max) {
			throw new ValidationException("Max file size is 5MB");
		}
		
		List<String> allowed=Arrays.asList("image/png","image/png","application/pdf","text/plain");
		
		if(!allowed.contains(file.getContentType())) {
			throw new ValidationException("Invalid file format");
		}
	}
	

}
