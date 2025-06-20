package com.hospitals.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospitals.entities.Hospital;

public interface HospitalRepo extends JpaRepository<Hospital, Long>  {
Optional<Hospital> findByName(String name);
   
}
