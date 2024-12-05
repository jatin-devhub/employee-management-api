package com.evolvedigitas.employee_management_api.controller;

import com.evolvedigitas.employee_management_api.dto.employee.EmployeeDTO;
import com.evolvedigitas.employee_management_api.dto.employee.EmployeeDetailsDTO;
import com.evolvedigitas.employee_management_api.dto.employee.EmployeeListDTO;
import com.evolvedigitas.employee_management_api.model.Employee;
import com.evolvedigitas.employee_management_api.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService= employeeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> emp= employeeService.getEmployeeById(id);
        HashMap<String, Object> customResponse= new HashMap<>();
        if(emp.isPresent()) {

            return new ResponseEntity<>(new EmployeeDetailsDTO(emp.get()), HttpStatus.OK);
        } else if (emp.isEmpty()) {
            customResponse.put("message", "Employee not found");
            return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
        }
        customResponse.put("message", "Server error");
        return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/all-employees")
    public ResponseEntity<?> getAllEmployees() {
        List<EmployeeListDTO> employeeList= employeeService.getAllEmployees();
        HashMap<String, Object> customResponse= new HashMap<>();
        if(employeeList!=null) {
            customResponse.put("employees", employeeList);
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        }
        customResponse.put("message", "Server Error");
        return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/new")
    public ResponseEntity<?> addEmployee(@ModelAttribute EmployeeDTO employeeDTO) throws IOException
    {
        HashMap<String, Object> customResponse= new HashMap<>();

        Employee employee= employeeDTO.toUser();

        if(employee.getAadharCard()==null || employee.getPanCard()==null || employee.getResume() ==null) {
            customResponse.put("message","Invalid data. Please ensure all required fields are provided.");
            return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
        }

        Long employeeId= employeeService.addEmployee(employee);
        if(employeeId!=null) {
            customResponse.put("message", "New employee added successfully");
            customResponse.put("employeeId", employeeId);
            return new ResponseEntity<>(customResponse, HttpStatus.CREATED);
        }
        customResponse.put("message", "Server error");
        return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
