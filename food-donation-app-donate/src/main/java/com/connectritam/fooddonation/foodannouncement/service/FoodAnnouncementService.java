package com.connectritam.fooddonation.foodannouncement.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectritam.fooddonation.foodannouncement.exception.ResourceNotFoundException;
import com.connectritam.fooddonation.foodannouncement.model.FoodAnnouncement;
import com.connectritam.fooddonation.foodannouncement.repository.FoodAnnouncementRepository;

@Service
public class FoodAnnouncementService {

    @Autowired
    private FoodAnnouncementRepository foodAnnouncementRepository;

    public List<FoodAnnouncement> getAllDonations() {
        return foodAnnouncementRepository.findAll();
    }

    public FoodAnnouncement getDonationById(UUID id) {
        return foodAnnouncementRepository.findById(id).orElse(null);
    }

    public FoodAnnouncement createDonation(FoodAnnouncement foodAnnouncement) {
        return foodAnnouncementRepository.save(foodAnnouncement);
    }

    public FoodAnnouncement updateDonation(FoodAnnouncement foodAnnouncement) {
        return foodAnnouncementRepository.save(foodAnnouncement);
    }

    public void deleteDonation(UUID id) {
        foodAnnouncementRepository.deleteById(id);
    }

    public void updateStatus(UUID announcementId, String status) {
        FoodAnnouncement announcement = foodAnnouncementRepository.findById(announcementId)
                .orElseThrow(() -> new ResourceNotFoundException("Food announcement not found"));
        announcement.setStatus(status);
        foodAnnouncementRepository.save(announcement);
    }

    public void markAsFulfilled(UUID announcementId) {
        updateStatus(announcementId, "FULFILLED");
    }

    public void markAsExpired(UUID announcementId) {
        updateStatus(announcementId, "EXPIRED");
    }

    public void markAsPending(UUID announcementId) {
        updateStatus(announcementId, "MATCHED");
    }

    public void markAsAvailable(UUID announcementId) {
        updateStatus(announcementId, "AVAILABLE");
    }

}