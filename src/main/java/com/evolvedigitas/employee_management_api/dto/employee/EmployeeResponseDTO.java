package com.evolvedigitas.employee_management_api.dto.employee;

import com.evolvedigitas.employee_management_api.model.Employee;
import com.evolvedigitas.employee_management_api.model.WorkExperience;
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
public class EmployeeResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private List<String> skills;
    private String position;
    private String department;
    private LocalDate dateJoined;
    private List<WorkExperienceResponseDTO> workExperiences;
    private List<EducationResponseDTO> education;
    private List<String> achievements;

    public EmployeeResponseDTO(Employee employee) {
        this.id= employee.getId();
        this.name= employee.getName();
        this.email= employee.getEmail();
        this.phone= employee.getPhone();
        this.skills= employee.getSkills();
        this.position= employee.getPosition();
        this.department= employee.getDepartment();
        this.dateJoined= employee.getDateJoined();
//        this.setWorkExperiences(
//                employee.getWorkExperiences().stream().map(workExperience -> {
//                    WorkExperienceResponseDTO workExperienceDTO = new WorkExperienceResponseDTO();
//                    workExperienceDTO.setCompanyName(workExperience.getCompanyName());
//                    workExperienceDTO.setDesignation(workExperience.getDesignation());
//                    workExperienceDTO.setStartMonth(workExperience.getStartMonth());
//                    workExperienceDTO.setStartYear(workExperience.getStartYear());
//                    workExperienceDTO.setEndMonth(workExperience.getEndMonth());
//                    workExperienceDTO.setEndYear(workExperience.getEndYear());
//                    if(workExperience.getExperienceDocument()!=null) {
//                        try {
//                            workExperienceDTO.setExperienceDocument(workExperience.getExperienceDocument());
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                    workExperience.setEmployee(employee); // Set back reference
//                    return workExperience;
//                }).toList()
//        );
//        this.workExperiences= employee.getWorkExperiences();
//        this.education= employee.getEducation();
//        this.achievements= employee.getAchievements();

    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
class WorkExperienceResponseDTO {
    private String companyName, designation;
    int startMonth, startYear, endMonth, endYear;
    private MultipartFile experienceDocument;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
class EducationResponseDTO {
    private String level, institution, grade, specialization;
    private int startYear, endYear;
}