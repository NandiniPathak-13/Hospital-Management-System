package com.hospitals.services;

import java.util.List;


import com.hospitals.entities.Doctor;


public interface Doctorservice {
 Doctor saveDoctor(Doctor doctor);
    Doctor updateDoctor(Long id, Doctor doctor);
    void deleteDoctor(Long id);
    Doctor getDoctorById(Long id);
    List<Doctor> getAllDoctors();
    List<Doctor> getDoctorsByHospitalId(Long hospitalId);
}
