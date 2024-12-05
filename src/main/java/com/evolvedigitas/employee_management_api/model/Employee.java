package com.evolvedigitas.employee_management_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name, email, phone, position, department, dateJoined;

    private List<String> skills;
    private List<String> workExperience;
    private List<String> achievements;

    @Lob
    @Column(name = "resume")
    private byte[] resume; // Stores file as binary

    @Lob
    @Column(name = "aadhar_card")
    private byte[] aadharCard; // Stores file as binary

    @Lob
    @Column(name = "pan_card")
    private byte[] panCard; // Stores file as binary

    private String education;



}
