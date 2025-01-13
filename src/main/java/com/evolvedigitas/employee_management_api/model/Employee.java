package com.evolvedigitas.employee_management_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name, email, phone, designation, department;
    private LocalDate dateJoined;

    private List<String> skills;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkExperience> workExperiences;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> education;

    private List<String> achievements;

    @OneToOne(cascade = CascadeType.ALL)
    private Document resume;

    @OneToOne(cascade = CascadeType.ALL)
    private Document aadhaarCard; // Stores file as binary

    @OneToOne(cascade = CascadeType.ALL)
    private Document panCard; // Stores file as binary
}
