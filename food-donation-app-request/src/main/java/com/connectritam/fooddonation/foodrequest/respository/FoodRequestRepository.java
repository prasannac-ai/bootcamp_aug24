package com.connectritam.fooddonation.foodrequest.respository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.connectritam.fooddonation.foodrequest.model.FoodRequest;

import java.util.UUID;

public interface FoodRequestRepository extends JpaRepository<FoodRequest, UUID> {
}