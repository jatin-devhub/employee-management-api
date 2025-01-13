package com.evolvedigitas.employee_management_api.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DocumentParsingService {

    public Map<String, Object> extractResumeData(MultipartFile file) throws Exception {
        String content = extractFileContent(file);

        HashMap<String, Object> data = new HashMap<>();

        data.put("name", extractNameFromResume(content));
        data.put("email", extractEmail(content));
        data.put("phone", extractPhone(content));

        data.put("skills", getAllSkills(content));
//        data.put("education", extractEducation(content));
//        data.put("extractedContent", content);
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

    public static String extractNameFromResume(String resumeText) {
        // Split text into lines
        String[] lines = resumeText.split("\\r?\\n");

        // Iterate through the lines to find the name
        for (String line : lines) {
            line = line.trim();

            // Skip empty lines
            if (line.isEmpty()) {
                continue;
            }

            // Check if the line is a valid name
            if (isLikelyName(line)) {
                return line;
            }
        }
        return null;
    }

    private static boolean isLikelyName(String line) {
        // Consider lines with 1-3 words, all uppercase or title case
        return line.matches("([A-Z]+\\s){1,2}[A-Z]+") || line.matches("([A-Z][a-z]+\\s){1,2}[A-Z][a-z]+");
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
        return null;
    }

    public static Set<String> getAllSkills(String resumeText) {
        String explicitSkills = extractSkills(resumeText);
        String projectSkills = extractSkillsFromProjects(resumeText);

        // Combine both, removing duplicates
        Set<String> allSkills = new LinkedHashSet<>();
        if (!explicitSkills.isEmpty()) {
            allSkills.addAll(Arrays.asList(explicitSkills.split(",\\s*")));
        }
        if (!projectSkills.isEmpty()) {
            allSkills.addAll(Arrays.asList(projectSkills.split(",\\s*")));
        }
        if(allSkills.isEmpty()) {
            return null;
        }

        return allSkills;
    }

    private static String extractSkills(String resumeText) {
        // Look for "SKILLS" or related keywords
        Pattern skillsSectionPattern = Pattern.compile("(?i)(skills|technical skills|key skills)\\s*:?\\s*(.*)");
        Matcher matcher = skillsSectionPattern.matcher(resumeText);

        StringBuilder skills = new StringBuilder();
        while (matcher.find()) {
            // Append skills mentioned after the heading
            skills.append(matcher.group(2)).append("\n");
        }

        // If no direct section found, fallback to project-based extraction
        if (skills.isEmpty()) {
            return extractSkillsFromProjects(resumeText);
        }
        return skills.toString().trim();
    }

    private static String extractSkillsFromProjects(String resumeText) {
        // Define a regex to capture technologies or tools
        Pattern techPattern = Pattern.compile(
                "(React[- ]?(Native)?|JavaScript|Java|Python|HTML|CSS|NodeJs|MongoDb|Flask|AI|Material UI|Bootstrap)",
                Pattern.CASE_INSENSITIVE);

        Matcher matcher = techPattern.matcher(resumeText);
        Set<String> skills = new LinkedHashSet<>(); // To avoid duplicates

        while (matcher.find()) {
            skills.add(matcher.group()); // Add skill to the set
        }

        return String.join(", ", skills);
    }

    private String extractEducation(String content) {
        // Dummy logic, replace with actual education parsing logic
        return "Education details extraction logic pending";
    }
}
