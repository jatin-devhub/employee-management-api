package com.evolvedigitas.employee_management_api.controller;

import com.evolvedigitas.employee_management_api.service.ParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/parsing")
public class DocumentParsing {

    @Autowired
    private final ParsingService parsingService;

    public DocumentParsing(ParsingService parsingService) {
        this.parsingService = parsingService;
    }

    @PostMapping("/upload-resume")
    public ResponseEntity<Map<String, Object>> uploadResume(@RequestParam("file")MultipartFile file) {
        try {
            Map<String, Object> extractedData = parsingService.extractResumeData(file);
            return ResponseEntity.ok(extractedData);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

}
