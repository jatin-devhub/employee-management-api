package com.evolvedigitas.employee_management_api.controller;

import com.evolvedigitas.employee_management_api.service.DocumentParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/parsing")
public class DocumentParsingController {

    @Autowired
    private final DocumentParsingService documentParsingService;

    public DocumentParsingController(DocumentParsingService documentParsingService) {
        this.documentParsingService = documentParsingService;
    }

    @PostMapping("/get-resume-details")
    public ResponseEntity<?> uploadResume(@RequestParam("file")MultipartFile file) {
        String contentType= file.getContentType();

        if(file.isEmpty() || !"application/pdf".equals(contentType)) {
            HashMap<String, Object> customResponse= new HashMap<>();
            customResponse.put("message","Invalid file format. Please upload a valid resume file.");
            return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            Map<String, Object> response = documentParsingService.extractResumeData(file);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

}
