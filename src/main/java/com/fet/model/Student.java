package com.fet.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Metric> metrics;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonManagedReference(value="diagnoses")
    private List<DiagnosisResult> diagnoses;
}
