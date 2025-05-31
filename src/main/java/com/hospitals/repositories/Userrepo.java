package com.hospitals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospitals.entities.User;

import java.util.Optional;


public interface Userrepo extends JpaRepository<User,Long> {

    

// Optional<User> findByEmail(String email);
Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    // List<User> findByRole(String role);

}
