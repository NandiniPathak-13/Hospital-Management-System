package com.hospitals.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospitals.entities.Appointment;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long>{

    // 🔍 Find all appointments by User ID
    List<Appointment> findByUserId(Long userId);

    // 🔍 Find all appointments by Hospital ID
    List<Appointment> findByHospitalId(Long hospitalId);

    // 🔍 Optional: search by phone number
    List<Appointment> findByPhoneNumber(String phoneNumber);
}
