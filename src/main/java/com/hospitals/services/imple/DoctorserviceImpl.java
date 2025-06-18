package com.hospitals.services.imple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitals.entities.Doctor;
import com.hospitals.repositories.Doctorrepo;
import com.hospitals.services.Doctorservice;


@Service
public class DoctorserviceImpl implements Doctorservice{


    
    @Autowired
    private Doctorrepo doctorRepository;


    @Override
    public Doctor saveDoctor(Doctor doctor) {
          return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        Doctor existing = doctorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));

        existing.setName(updatedDoctor.getName());
        existing.setSpecialization(updatedDoctor.getSpecialization());
        existing.setHospitalname(updatedDoctor.getHospitalname());
        existing.setHospital(updatedDoctor.getHospital());

        return doctorRepository.save(existing);
     }

    @Override
    public void deleteDoctor(Long id) {
         doctorRepository.deleteById(id);
         }

    @Override
    public Doctor getDoctorById(Long id) {
         return doctorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
  }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
       }

    @Override
    public List<Doctor> getDoctorsByHospitalId(Long hospitalId) {
         return doctorRepository.findByHospitalId(hospitalId);    }

}
