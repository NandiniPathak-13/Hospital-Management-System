package com.hospitals.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "appointment")
@Table(name = "appointment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientName;
    private String phoneNumber;
    private String details;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

     @ManyToOne
    @JoinColumn(name = "doctor_id") // ✅ Important
    private Doctor doctor;

    // getters and setters
}
