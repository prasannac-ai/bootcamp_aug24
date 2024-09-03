package com.connectritam.fooddonation.foodannouncement.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.connectritam.fooddonation.foodannouncement.model.FoodAnnouncement;
import com.connectritam.fooddonation.foodannouncement.service.DonationProducer;
import com.connectritam.fooddonation.foodannouncement.service.FoodAnnouncementService;

@RestController
@RequestMapping("/v1/food-donations")
public class DonationController {

    private final FoodAnnouncementService foodAnnouncementService;

    final DonationProducer producer;

    DonationController(DonationProducer producer, FoodAnnouncementService foodAnnouncementService) {
        this.producer = producer;
        this.foodAnnouncementService = foodAnnouncementService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')  or hasAuthority('ROLE_INTERNAL_SERVICE')")
    public List<FoodAnnouncement> getAllDonations() {
        return foodAnnouncementService.getAllDonations();
    }

    @GetMapping("/{id}") // STUDENT Practice DTO implmentation for FoodAnnouncement Model
    public ResponseEntity<FoodAnnouncement> getDonationById(@PathVariable UUID id) {
        FoodAnnouncement foodAnnouncement = foodAnnouncementService.getDonationById(id);
        if (foodAnnouncement == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foodAnnouncement);
    }

    @PostMapping
    public FoodAnnouncement createDonation(@RequestBody FoodAnnouncement foodAnnouncement) {
        FoodAnnouncement donation = foodAnnouncementService.createDonation(foodAnnouncement);

        try {

            producer.sendMessage(donation.getId().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return donation;
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodAnnouncement> updateDonation(@PathVariable UUID id,
            @RequestBody FoodAnnouncement foodAnnouncementDetails) {
        FoodAnnouncement foodAnnouncement = foodAnnouncementService.getDonationById(id);
        if (foodAnnouncement == null) {
            return ResponseEntity.notFound().build();
        }
        foodAnnouncement.setDonorId(foodAnnouncementDetails.getDonorId());
        foodAnnouncement.setFoodCategory(foodAnnouncementDetails.getFoodCategory());
        foodAnnouncement.setFoodType(foodAnnouncementDetails.getFoodType());
        foodAnnouncement.setQuantity(foodAnnouncementDetails.getQuantity());
        foodAnnouncement.setAvailabilityTime(foodAnnouncementDetails.getAvailabilityTime());
        foodAnnouncement.setLocation(foodAnnouncementDetails.getLocation());
        foodAnnouncement.setLatitude(foodAnnouncementDetails.getLatitude());
        foodAnnouncement.setLongitude(foodAnnouncementDetails.getLongitude());
        foodAnnouncement.setUpdatedAt(LocalDateTime.now());
        FoodAnnouncement updatedAnnouncement = foodAnnouncementService.updateDonation(foodAnnouncement);
        return ResponseEntity.ok(updatedAnnouncement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable UUID id) {
        FoodAnnouncement foodAnnouncement = foodAnnouncementService.getDonationById(id);
        if (foodAnnouncement == null) {
            return ResponseEntity.notFound().build();
        }
        foodAnnouncementService.deleteDonation(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable UUID id, @RequestParam String status) {
        foodAnnouncementService.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }

}