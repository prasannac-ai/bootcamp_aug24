package com.connectritam.fooddonation.match.client;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.connectritam.fooddonation.match.model.FoodRequest;

@FeignClient(name = "food-request-service", url = "${food.request.service.url}")
public interface FoodRequestClient {
    @GetMapping("/v1/food-request")
    List<FoodRequest> getAllRequests();

    @GetMapping("/v1/food-request/{id}")
    FoodRequest getRequestById(@PathVariable("id") UUID id);

    @PutMapping("/v1/food-request/{id}/status")
    void updateStatus(@PathVariable("id") UUID id, @RequestParam("status") String status);
}
