package com.connectritam.fooddonation.userservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.connectritam.fooddonation.userservice.model.Users;

public interface UserRepository extends JpaRepository<Users, UUID> {

    public Users findByEmail(String email);

}
