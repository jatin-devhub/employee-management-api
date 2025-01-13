package com.evolvedigitas.employee_management_api.repo;

import com.evolvedigitas.employee_management_api.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
}
