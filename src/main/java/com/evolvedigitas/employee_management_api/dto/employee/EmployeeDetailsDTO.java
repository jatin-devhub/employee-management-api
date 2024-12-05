package com.evolvedigitas.employee_management_api.dto.employee;

import com.evolvedigitas.employee_management_api.model.Employee;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDetailsDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private List<String> skills;
    private String position;
    private String department;
    private String dateJoined;
    private List<String> workExperiences;
    private String education;
    private List<String> achievements;

    public EmployeeDetailsDTO(Employee employee) {
        this.id= employee.getId();
        this.name= employee.getName();
        this.email= employee.getEmail();
        this.phone= employee.getPhone();
        this.skills= employee.getSkills();
        this.position= employee.getPosition();
        this.department= employee.getDepartment();
        this.dateJoined= employee.getDateJoined();
        this.workExperiences= employee.getWorkExperience();
        this.education= employee.getEducation();
        this.achievements= employee.getAchievements();

    }



}
