package com.evolvedigitas.employee_management_api.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ParsingService {

    public Map<String, Object> extractResumeData(MultipartFile file) throws Exception {
        String content = extractFileContent(file);

        HashMap<String, Object> data = new HashMap<>();

        data.put("name", extractName(content));
        data.put("email", extractEmail(content));
        data.put("phone", extractPhone(content));
//        data.put("skills", extractSkills(content));
//        data.put("education", extractEducation(content));
        data.put("extratedContent", content);
        return data;
    }

    private String extractFileContent(MultipartFile file) throws Exception {
        String content = "";
        if (file.getOriginalFilename().endsWith(".pdf")) {
            byte[] fileBytes = file.getInputStream().readAllBytes();
            try (PDDocument document = Loader.loadPDF(fileBytes)) {
                PDFTextStripper stripper = new PDFTextStripper();
                content = stripper.getText(document);
            }
        } else if(file.getOriginalFilename().endsWith(".docx")) {
            try (InputStream inputStream = file.getInputStream(); XWPFDocument document = new XWPFDocument(inputStream)) {
                content = document.getParagraphs().stream()
                        .map(p -> p.getText())
                        .reduce("", (a, b) -> a + "\n" + b);
            }
        } else {
            throw new IllegalAccessException("Unsupported File Type");
        }
        return content;
    }

    private String extractName(String extractedContent){
        return extractUsingRegex(extractedContent, "(\\b[A-Z][a-z]+\\b)\\s(\\b[A-Z][a-z]+\\b)");
    }

    private String extractPhone(String extractedContent){
        return extractUsingRegex(extractedContent, "\\b(?:\\+?\\d{1,3}[-.\\s]?)?\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}\\b");
    }

    private String extractEmail(String extractedContent){
        return extractUsingRegex(extractedContent, "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b");
    }

    private String extractUsingRegex(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group();
        }
        return "Not Found";
    }

    private String extractSkills(String content) {
        // Dummy logic, replace with actual skill extraction logic
        if (content.contains("Java")) return "Java, Spring Boot";
        return "Not Found";
    }

    private String extractEducation(String content) {
        // Dummy logic, replace with actual education parsing logic
        return "Education details extraction logic pending";
    }
}
