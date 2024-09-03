package com.connectritam.fooddonation.foodrequest.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectritam.fooddonation.foodrequest.model.FoodRequest;
import com.connectritam.fooddonation.foodrequest.respository.FoodRequestRepository;

@Service
public class FoodRequestService {

    @Autowired
    private FoodRequestRepository foodRequestRepository;

    public List<FoodRequest> getAllRequests() {
        return foodRequestRepository.findAll();
    }

    public FoodRequest getRequestById(UUID id) {
        return foodRequestRepository.findById(id).orElse(null);
    }

    public FoodRequest createRequest(FoodRequest foodRequest) {
        return foodRequestRepository.save(foodRequest);
    }

    public FoodRequest updateRequest(FoodRequest foodRequest) {
        return foodRequestRepository.save(foodRequest);
    }

    public void deleteRequest(UUID id) {
        foodRequestRepository.deleteById(id);
    }

    public void updateStatus(UUID requestId, String status) {
        FoodRequest request = foodRequestRepository.findById(requestId)
                .orElse(null);
        request.setStatus(status);
        foodRequestRepository.save(request);
    }

    public void markAsFulfilled(UUID requestId) {
        updateStatus(requestId, "FULFILLED");
    }

    public void markAsCancelled(UUID requestId) {
        updateStatus(requestId, "CANCELLED");
    }

    public void markAsPending(UUID requestId) {
        updateStatus(requestId, "MATCHED");
    }

    public void markAsAvailable(UUID requestId) {
        updateStatus(requestId, "AVAILABLE");
    }

}