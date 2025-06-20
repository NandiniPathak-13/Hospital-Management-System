package com.hospitals.services;

import java.util.List;
import java.util.Optional;

import com.hospitals.entities.Hospital;


public interface Hospitalservice {

     Hospital saveHospital(Hospital hospital);

    Optional<Hospital> getHospitalById (long id);

    Optional<Hospital> updateHospital(Hospital hospital);

    void deleteHospital(long id);
  Hospital getById(Long id);
    boolean isHospitalExist(long id);
   Hospital getByName(String name); //
  

    List<Hospital> getAllHospitals();

   
}
