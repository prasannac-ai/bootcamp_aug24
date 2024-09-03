package com.connectritam.fooddonation.foodrequest.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.connectritam.fooddonation.foodrequest.model.FoodRequest;
import com.connectritam.fooddonation.foodrequest.service.FoodRequestService;

@RestController
@RequestMapping("/v1/food-request")
public class FoodRequestController {

    private final FoodRequestService foodRequestService;

    FoodRequestController(FoodRequestService foodRequestService) {
        this.foodRequestService = foodRequestService;
    }

    @GetMapping
    public List<FoodRequest> getAllRequests() {
        return foodRequestService.getAllRequests();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodRequest> getRequestById(@PathVariable UUID id) {
        FoodRequest foodRequest = foodRequestService.getRequestById(id);
        if (foodRequest == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foodRequest);
    }

    @PostMapping
    public FoodRequest createRequest(@RequestBody FoodRequest foodRequest) {
        return foodRequestService.createRequest(foodRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodRequest> updateRequest(@PathVariable UUID id,
            @RequestBody FoodRequest foodRequestDetails) {
        FoodRequest foodRequest = foodRequestService.getRequestById(id);
        if (foodRequest == null) {
            return ResponseEntity.notFound().build();
        }
        foodRequest.setCollectorId(foodRequestDetails.getCollectorId());
        foodRequest.setFoodCategory(foodRequestDetails.getFoodCategory());
        foodRequest.setFoodType(foodRequestDetails.getFoodType());
        foodRequest.setQuantity(foodRequestDetails.getQuantity());
        foodRequest.setRequestTime(foodRequestDetails.getRequestTime());
        foodRequest.setLocation(foodRequestDetails.getLocation());
        foodRequest.setLatitude(foodRequestDetails.getLatitude());
        foodRequest.setLongitude(foodRequestDetails.getLongitude());
        foodRequest.setUpdatedAt(LocalDateTime.now());
        FoodRequest updatedRequest = foodRequestService.updateRequest(foodRequest);
        return ResponseEntity.ok(updatedRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable UUID id) {
        FoodRequest foodRequest = foodRequestService.getRequestById(id);
        if (foodRequest == null) {
            return ResponseEntity.notFound().build();
        }
        foodRequestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable UUID id, @RequestParam String status) {
        foodRequestService.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }

}