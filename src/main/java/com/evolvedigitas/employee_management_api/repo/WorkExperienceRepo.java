package com.evolvedigitas.employee_management_api.repo;

import com.evolvedigitas.employee_management_api.model.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkExperienceRepo extends JpaRepository<WorkExperience, Long> {
}
