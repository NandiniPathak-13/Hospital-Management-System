package com.hospitals.forms;

import java.time.LocalDate;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentForm {

    private String patientName;

    private String phoneNumber;

    private String details;
    private Long doctorId;
    private Long userId;
    private LocalDate date;
    private Long hospitalId;
}
