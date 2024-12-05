package com.evolvedigitas.employee_management_api.dto.employee;

import com.evolvedigitas.employee_management_api.model.Employee;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDTO {

    private Long id; // Optional, include only if needed for updates or responses

    private String name;
    private String email;
    private String phone;
    private String position;
    private String department;
    private String dateJoined;

    private List<String> skills;
    private List<String> workExperience;
    private List<String> achievements;

    private MultipartFile resume; // File upload field
    private MultipartFile aadharCard; // File upload field
    private MultipartFile panCard; // File upload field

    private String education;

    public Employee toUser() throws IOException {
        // Map DTO to Entity
        Employee employee = new Employee();

        employee.setName(this.getName());
        employee.setEmail(this.getEmail());
        employee.setPhone(this.getPhone());
        employee.setPosition(this.getPosition());
        employee.setDepartment(this.getDepartment());
        employee.setDateJoined(this.getDateJoined());

        employee.setSkills(this.getSkills());
        employee.setWorkExperience(this.getWorkExperience());
        employee.setAchievements(this.getAchievements());

        if(this.getResume()!=null) {
            employee.setResume(this.getResume().getBytes());
        }
        if(this.getAadharCard()!=null) {
            employee.setAadharCard(this.getAadharCard().getBytes());
        }
        if(this.getPanCard()!=null) {
            employee.setPanCard(this.getPanCard().getBytes());
        }

        employee.setEducation(this.getEducation());

        return employee;
    }

}
