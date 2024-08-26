package com.connectritam.fooddonation.userservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectritam.fooddonation.userservice.model.Users;
import com.connectritam.fooddonation.userservice.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<Users> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public Users createUser(Users user) {
        return userRepository.save(user);
    }

    public Users updateUser(UUID id, Users userDetails) {
        Optional<Users> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setRole(userDetails.getRole());
            user.setMobile(userDetails.getMobile());
            user.setAddress(userDetails.getAddress());
            user.setLatitude(userDetails.getLatitude());
            user.setLongitude(userDetails.getLongitude());
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

}