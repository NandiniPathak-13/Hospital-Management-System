package com.hospitals.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospitals.entities.Doctor;


public interface Doctorrepo extends JpaRepository<Doctor, Long> {
 List<Doctor> findByHospitalId(Long hospitalId);
  Optional<Doctor> findById(Long id);
}
