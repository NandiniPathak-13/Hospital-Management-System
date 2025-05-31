package com.hospitals.services;

import java.util.List;
import java.util.Optional;

import com.hospitals.entities.User;

public interface Userservice {

    User saveUser(User user);

    Optional<User> getUserById(long id);

    Optional<User> updateUser(User user);

    void deleteUser(long id);

    boolean isUserExist(long id);

    boolean isUserExistByUsername(String email);

    List<User> getAllUsers();

    User getUserByEmail(String email);

}
