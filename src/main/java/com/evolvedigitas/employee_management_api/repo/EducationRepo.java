package com.evolvedigitas.employee_management_api.repo;

import com.evolvedigitas.employee_management_api.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepo extends JpaRepository<Education, Long> {
}
