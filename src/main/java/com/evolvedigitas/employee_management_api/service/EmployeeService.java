package com.evolvedigitas.employee_management_api.service;

import com.evolvedigitas.employee_management_api.dto.employee.EmployeeListDTO;
import com.evolvedigitas.employee_management_api.model.Employee;
import com.evolvedigitas.employee_management_api.repo.EmployeeRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo= employeeRepo;
    }


    public Long addEmployee(Employee employee) {
        try {
            Employee res= employeeRepo.save(employee);
            return res.getId();
        } catch (Exception e) {
            return null;
        }
    }

    public List<EmployeeListDTO> getAllEmployees() {
        try {
            List<Employee> employeeList= employeeRepo.findAll();
            List<EmployeeListDTO> employeeListDTOS= new ArrayList<>();
            for(Employee emp: employeeList) {
                EmployeeListDTO empDTO= new EmployeeListDTO(emp);
                employeeListDTOS.add(empDTO);
            }
            return employeeListDTOS;
        } catch (Exception e) {
            return null;
        }
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepo.findById(id);
    }
}
