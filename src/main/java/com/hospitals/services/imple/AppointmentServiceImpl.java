package com.hospitals.services.imple;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitals.entities.Appointment;
import com.hospitals.repositories.AppointmentRepo;
import com.hospitals.services.AppointmentService;


@Service
public class AppointmentServiceImpl implements AppointmentService{

  @Autowired
    private AppointmentRepo appointmentRepo;

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepo.save(appointment);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    @Override
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepo.findById(id);
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepo.deleteById(id);
    }

    @Override
    public List<Appointment> getAppointmentsByUserId(Long userId) {
        return appointmentRepo.findByUserId(userId);
    }

    @Override
    public List<Appointment> getAppointmentsByHospitalId(Long hospitalId) {
        return appointmentRepo.findByHospitalId(hospitalId);
    }

}
