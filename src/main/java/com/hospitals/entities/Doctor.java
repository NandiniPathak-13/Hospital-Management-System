package com.hospitals.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "doctor")
@Table(name = "doctor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private String specialization;
    private String hospitalname;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    // getters and setters
}
