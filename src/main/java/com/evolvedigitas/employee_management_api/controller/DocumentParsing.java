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
    private String extractName(String content) {
        return content.lines().findFirst().orElse("Name not found");
    }
    private String extractEmail(String content) {
        return content.matches(".*([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}).*")
                ? content.replaceAll(".*([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}).*", "$1")
                : "Email not found";
    }

    private String extractPhone(String content) {
        return content.matches(".*(\\d{10}).*")
                ? content.replaceAll(".*(\\d{10}).*", "$1")
                : "Phone not found";
    }

    private String extractSkills(String content) {
        // Basic keywords matching
        return content.contains("Java") ? "Java" : "Skills not found";
    }

    private String extractWorkExperience(String content) {
        // Logic to extract work experience
        return content.contains("experience") ? "Experience mentioned" : "Experience not found";
    }
}
