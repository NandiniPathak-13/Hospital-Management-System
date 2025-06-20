package com.hospitals.services;

import java.util.List;
import java.util.Optional;

import com.hospitals.entities.Appointment;

public interface AppointmentService {
    Appointment saveAppointment(Appointment appointment);

    List<Appointment> getAllAppointments();

    Optional<Appointment> getAppointmentById(Long id);

    void deleteAppointment(Long id);

    List<Appointment> getAppointmentsByUserId(Long userId);

    List<Appointment> getAppointmentsByHospitalId(Long hospitalId);

}
