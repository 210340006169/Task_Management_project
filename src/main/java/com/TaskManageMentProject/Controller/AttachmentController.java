package com.TaskManageMentProject.Controller;

import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.TaskManageMentProject.Entity.Attachment;
import com.TaskManageMentProject.Service.AttachmentService;

import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    // Upload file
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam Long issueId,
            @RequestParam MultipartFile file,
            @RequestParam String uploadedBy) {

        try {
            Attachment attachment =
                    attachmentService.uploadFile(issueId, file, uploadedBy);
            return ResponseEntity.status(HttpStatus.CREATED).body(attachment);

        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (FileUploadException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File upload failed");
        }
    }

    // Get files by issue ID
    @GetMapping("/issue/{issueId}")
    public ResponseEntity<List<Attachment>> getFilesByIssueId(
            @PathVariable Long issueId) {

        return ResponseEntity.ok(
                attachmentService.getFileByIssueId(issueId)
        );
    }

    // Get file by attachment ID
    @GetMapping("/{id}")
    public ResponseEntity<Attachment> getFileById(@PathVariable Long id) {

        return ResponseEntity.ok(
                attachmentService.getFileById(id)
        );
    }

    // Delete file
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) {

        try {
            attachmentService.deleteFile(id);
            return ResponseEntity.ok("Attachment deleted successfully");

        } catch (FileUploadException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete attachment");
        }
    }
}
