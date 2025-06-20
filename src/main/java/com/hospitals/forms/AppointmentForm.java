package com.hospitals.forms;



import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentForm {

    private String patientName;

    private String phoneNumber;

    private String details;

    private Long userId;

    private Long hospitalId;
}
