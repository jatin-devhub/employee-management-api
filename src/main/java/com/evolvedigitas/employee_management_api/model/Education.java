package com.evolvedigitas.employee_management_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String level, institution, grade, specialization;
    private int startYear, endYear;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @OneToOne(cascade = CascadeType.ALL)
    private Document educationDocument;

    @Override
    public String toString() {
        return "Employee{id=" + id + ", level='" + level + "', institution='" + institution + "', grade='" + grade + "'}";
    }
}
