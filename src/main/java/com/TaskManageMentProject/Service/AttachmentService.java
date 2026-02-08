package com.TaskManageMentProject.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.TaskManageMentProject.Entity.Attachment;
import com.TaskManageMentProject.Repository.AttachmentRepository;
import com.cloudinary.Cloudinary;

import jakarta.validation.ValidationException;

@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepo;

    @Autowired
    private Cloudinary cloudinary;

    public Attachment uploadFile(Long issueId, MultipartFile file, String uploadBy)
            throws FileUploadException, ValidationException {

        validateFile(file);

        try {
            Map<String, Object> uploadOptions = new HashMap<>();
            uploadOptions.put("resource_type", "auto");

            Map uploadResult = cloudinary.uploader()
                    .upload(file.getBytes(), uploadOptions);

            Attachment attachment = new Attachment();
            attachment.setIssueId(issueId);
            attachment.setFileName(file.getOriginalFilename());
            attachment.setContentType(file.getContentType());
            attachment.setStoragePath(uploadResult.get("secure_url").toString());
            attachment.setCloudId(uploadResult.get("public_id").toString());
            attachment.setUploadedBy(uploadBy);

            return attachmentRepo.save(attachment);

        } catch (Exception e) {
            throw new FileUploadException("File upload failed", e);
        }
    }

    public List<Attachment> getFileByIssueId(Long issueId) {
        return attachmentRepo.findByIssueId(issueId);
    }

    public Attachment getFileById(Long id) {
        return attachmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Attachment not found"));
    }

    public void deleteFile(Long id) throws FileUploadException {

        Attachment attachment = attachmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Attachment not found"));

        try {
            Map<String, Object> options = new HashMap<>();
            options.put("resource_type", "auto");

            cloudinary.uploader().destroy(attachment.getCloudId(), options);
            attachmentRepo.delete(attachment);

        } catch (Exception e) {
            throw new FileUploadException("Delete failed", e);
        }
    }

    private void validateFile(MultipartFile file) throws ValidationException {

        if (file.isEmpty()) {
            throw new ValidationException("File cannot be empty");
        }

        long maxSize = 5 * 1024 * 1024;
        if (file.getSize() > maxSize) {
            throw new ValidationException("Max file size is 5MB");
        }

        List<String> allowedTypes = Arrays.asList(
                "image/png",
                "image/jpeg",
                "application/pdf",
                "text/plain"
        );

        if (!allowedTypes.contains(file.getContentType())) {
            throw new ValidationException("Invalid file format");
        }
    }
}
