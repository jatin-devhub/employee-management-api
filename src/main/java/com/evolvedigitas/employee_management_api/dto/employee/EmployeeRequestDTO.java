package com.evolvedigitas.employee_management_api.dto.employee;

import com.evolvedigitas.employee_management_api.model.Document;
import com.evolvedigitas.employee_management_api.model.Education;
import com.evolvedigitas.employee_management_api.model.Employee;
import com.evolvedigitas.employee_management_api.model.WorkExperience;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid Email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Designation is required")
    private String designation;

    @NotBlank(message = "Department is required")
    private String department;

    @PastOrPresent(message = "Joining date cannot be in the future")
    @NotBlank(message = "Joining date is required")
    private LocalDate dateJoined;

    private List<String> skills;

    private List<WorkExperienceRequestDTO> workExperiences;

    private List<String> achievements;

    @NotBlank(message = "Resume is required")
    private MultipartFile resume;

    @NotBlank(message = "Aadhaar Card is required")
    private MultipartFile aadhaarCard;

    @NotBlank(message = "Pan Card is required")
    private MultipartFile panCard;

    private List<EducationRequestDTO> education;

    public Employee toUser() throws IOException {
        // Map DTO to Entity
        Employee employee = new Employee();

        employee.setName(this.getName());
        employee.setEmail(this.getEmail());
        employee.setPhone(this.getPhone());
        employee.setDesignation(this.getDesignation());
        employee.setDepartment(this.getDepartment());
        employee.setDateJoined(this.getDateJoined());

        employee.setSkills(this.getSkills());
        employee.setWorkExperiences(
                this.getWorkExperiences().stream().map(workExperienceDTO -> {
                    WorkExperience workExperience = new WorkExperience();
                    workExperience.setCompanyName(workExperienceDTO.getCompanyName());
                    workExperience.setDesignation(workExperienceDTO.getDesignation());
                    workExperience.setStartMonth(workExperienceDTO.getStartMonth());
                    workExperience.setStartYear(workExperienceDTO.getStartYear());
                    workExperience.setEndMonth(workExperienceDTO.getEndMonth());
                    workExperience.setEndYear(workExperienceDTO.getEndYear());
                    if(workExperienceDTO.getExperienceDocument()!=null) {
                        try {
                            Document experienceDocument = getDocument(workExperienceDTO.getExperienceDocument());
                            workExperience.setExperienceDocument(experienceDocument);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    workExperience.setEmployee(employee); // Set back reference
                    return workExperience;
                }).toList()
        );
        employee.setAchievements(this.getAchievements());

        if(this.getResume()!=null) {
            employee.setResume(getDocument(this.getResume()));
        }
        if(this.getAadhaarCard()!=null) {
            employee.setAadhaarCard(getDocument(this.getAadhaarCard()));
        }
        if(this.getPanCard()!=null) {
            employee.setPanCard(getDocument(this.getPanCard()));
        }

        employee.setEducation(
                this.getEducation().stream().map(educationDTO -> {
                    Education education = new Education();
                    education.setLevel(educationDTO.getLevel());
                    education.setInstitution(educationDTO.getInstitution());
                    education.setGrade(educationDTO.getGrade());
                    education.setSpecialization(educationDTO.getSpecialization());
                    education.setStartYear(educationDTO.getStartYear());
                    education.setEndYear(educationDTO.getEndYear());
                    return education;
                }).toList()
        );

        return employee;
    }

    private static Document getDocument(MultipartFile multipartFile) throws IOException {
        Document experienceDocument = new Document();
        experienceDocument.setName(multipartFile.getOriginalFilename());
        experienceDocument.setType(multipartFile.getContentType());
        experienceDocument.setSize(multipartFile.getSize());
        experienceDocument.setContent(multipartFile.getBytes());
        return experienceDocument;
    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
class WorkExperienceRequestDTO {

    @NotBlank(message = "Company Name is required in work experience")
    private String companyName;

    @NotBlank(message = "Designation is required in work experience")
    private String designation;

    @NotBlank(message = "work experience starting month and year are required")
    @Min(value = 1, message = "Month must be between 1 and 12")
    @Max(value = 12, message = "Month must be between 1 and 12")
    int startMonth, endMonth;

    @NotBlank(message = "work experience starting month and year are required")
    @Min(value = 1980, message = "Please enter valid work experience year")
    int startYear, endYear;

    @NotBlank(message = "Experience Proof is required")
    private MultipartFile experienceDocument;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
class EducationRequestDTO {
    @NotBlank(message = "Education level is required")
    private String level;

    @NotBlank(message = "Education institution is required")
    private String institution;

    @NotBlank(message = "Grade is required")
    private String grade;

    private String specialization;

    @NotBlank(message = "Education start year is required")
    @Min(value = 1980, message = "Please enter valid education starting year")
    private int startYear;

    @Min(value = 1980, message = "Please enter valid education ending year")
    private int endYear;

    @NotBlank(message = "Education Proof is required")
    private MultipartFile educationDocument;
}
