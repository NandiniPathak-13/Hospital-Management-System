package com.hospitals.services;

import java.util.List;
import java.util.Optional;

import com.hospitals.entities.Hospital;


public interface Hospitalservice {

     Hospital saveHospital(Hospital hospital);

    Optional<Hospital> getHospitalById (long id);

    Optional<Hospital> updateHospital(Hospital hospital);

    void deleteHospital(long id);

    boolean isHospitalExist(long id);

  

    List<Hospital> getAllHospitals();

   
}
