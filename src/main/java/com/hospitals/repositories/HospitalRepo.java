package com.hospitals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospitals.entities.Hospital;

public interface HospitalRepo extends JpaRepository<Hospital, Long>  {

}
