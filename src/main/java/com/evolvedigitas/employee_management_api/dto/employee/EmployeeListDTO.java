package com.evolvedigitas.employee_management_api.dto.employee;

import com.evolvedigitas.employee_management_api.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeListDTO {
    private Long id;
    private String name;
    private String workMailId;
    private String position;
    private String department;
    private String dateJoined;

    public EmployeeListDTO(Employee employee) {
        this.id= employee.getId();
        this.name= employee.getName();
        this.workMailId= employee.getEmail();
        this.position= employee.getPosition();
        this.department= employee.getDepartment();
        this.dateJoined= employee.getDateJoined();
    }

}
