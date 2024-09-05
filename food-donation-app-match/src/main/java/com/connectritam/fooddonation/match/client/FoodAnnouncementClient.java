package com.connectritam.fooddonation.match.client;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.connectritam.fooddonation.match.config.FeignClientConfig;
import com.connectritam.fooddonation.match.model.FoodAnnouncement;

@FeignClient(name = "food-announcement-service", url = "${food.announcement.service.url}", configuration = FeignClientConfig.class)
public interface FoodAnnouncementClient {

    @GetMapping("/v1/food-donations/{id}")
    FoodAnnouncement getDonationById(@PathVariable("id") UUID id);

    @GetMapping("/v1/food-donations")
    List<FoodAnnouncement> getAllDonations();

    @PutMapping("/v1/food-donations/{id}/status")
    void updateStatus(@PathVariable("id") UUID id, @RequestParam("status") String status);
}