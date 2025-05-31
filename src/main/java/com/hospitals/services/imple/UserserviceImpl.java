package com.hospitals.services.imple;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospitals.helpers.*;
import com.hospitals.entities.User;
import com.hospitals.repositories.Userrepo;
import com.hospitals.services.Userservice;

@Service
public class UserserviceImpl implements Userservice {

    @Autowired
    private Userrepo userrepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(List.of(AppConstant.ROLE_USER));
        return userrepo.save(user);
    }

    @Override
    public Optional<User> getUserById(long id) {
        return userrepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User existingUser = userrepo.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not found"));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhoneNumber(user.getPhoneNumber());

        // Password encoding only if it's changed
        if (!user.getPassword().equals(existingUser.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        User updatedUser = userrepo.save(existingUser);
        return Optional.of(updatedUser);
    }

    @Override
    public void deleteUser(long id) {
        if (userrepo.existsById(id)) {
            userrepo.deleteById(id);
        } else {
            logger.warn("Tried to delete non-existent user with ID: {}", id);
        }
    }

    @Override
    public boolean isUserExist(long id) {
        return userrepo.existsById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userrepo.findAll();
    }

    @Override
    public boolean isUserExistByUsername(String email) {
        User user = userrepo.findByEmail(email).orElse(null);
        return user != null;
    }

    @Override
    public User getUserByEmail(String email) {
        return userrepo.findByEmail(email).orElse(null);
    }
}
