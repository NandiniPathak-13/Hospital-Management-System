package com.hospitals.services.imple;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitals.entities.Hospital;
import com.hospitals.repositories.HospitalRepo;
import com.hospitals.services.Hospitalservice;

@Service
public class HospitalserviceImpl implements Hospitalservice {

    @Autowired
    private HospitalRepo hospitalRepo;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Hospital saveHospital(Hospital hospital) {
        logger.info("Saving hospital: {}", hospital.getName());
        return hospitalRepo.save(hospital);
    }

    @Override
    public Optional<Hospital> getHospitalById(long id) {
        logger.info("Fetching hospital with ID: {}", id);
        return hospitalRepo.findById(id);
    }

    @Override
    public Optional<Hospital> updateHospital(Hospital hospital) {
        logger.info("Updating hospital with ID: {}", hospital.getId());
        if (hospitalRepo.existsById(hospital.getId())) {
            Hospital updatedHospital = hospitalRepo.save(hospital);
            return Optional.of(updatedHospital);
        } else {
            logger.warn("Hospital with ID {} not found", hospital.getId());
            return Optional.empty();
        }
    }
@Override
public Hospital getById(Long id) {
    return hospitalRepo.findById(id).orElseThrow(() -> new RuntimeException("Hospital not found"));
}

    @Override
    public void deleteHospital(long id) {
        logger.info("Deleting hospital with ID: {}", id);
        hospitalRepo.deleteById(id);
    }

    @Override
    public boolean isHospitalExist(long id) {
        boolean exists = hospitalRepo.existsById(id);
        logger.info("Hospital with ID {} exists: {}", id, exists);
        return exists;
    }

    @Override
    public List<Hospital> getAllHospitals() {
        logger.info("Fetching all hospitals");
        return hospitalRepo.findAll();
    }

    @Override
    public Hospital getByName(String name) {
        return hospitalRepo.findByName(name)
            .orElseThrow(() -> new RuntimeException("Hospital not found with name: " + name));
   }
}
