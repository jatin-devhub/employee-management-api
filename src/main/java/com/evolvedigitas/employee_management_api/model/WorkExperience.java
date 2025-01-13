package com.evolvedigitas.employee_management_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName, designation;
    int startMonth, startYear, endMonth, endYear;

    @OneToOne(cascade = CascadeType.ALL)
    private Document experienceDocument;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Override
    public String toString() {
        return "WorkExperience{id=" + id + ", companyName='" + companyName + "', designation='" + designation + "'}";
    }

}
