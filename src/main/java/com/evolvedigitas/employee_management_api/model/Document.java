package com.evolvedigitas.employee_management_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private long size;

    @Lob
    @Column(nullable = false)
    private byte[] content;

    @Override
    public String toString() {
        return "Document{id=" + this.id + ", title='" + this.name + "'}";
    }
}
