package com.connectritam.fooddonation.match.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectritam.fooddonation.match.client.FoodAnnouncementClient;
import com.connectritam.fooddonation.match.client.FoodRequestClient;
import com.connectritam.fooddonation.match.model.FoodAnnouncement;
import com.connectritam.fooddonation.match.model.FoodRequest;
import com.connectritam.fooddonation.match.model.Match;

@Service
public class MatchingService {

    private static final Logger logger = LoggerFactory.getLogger(MatchingService.class);

    @Autowired
    private FoodRequestClient foodRequestClient;

    @Autowired
    private FoodAnnouncementClient foodAnnouncementClient;

    public List<Match> findMatches(UUID requestId) {
        FoodRequest foodRequest = foodRequestClient.getRequestById(requestId);
        if (foodRequest == null) {
            throw new IllegalArgumentException("Food request not found with id: " + requestId);
        }

        List<FoodAnnouncement> foodAnnouncements = foodAnnouncementClient.getAllDonations();
        return foodAnnouncements.stream()
                .filter(announcement -> isMatch(foodRequest, announcement))
                .map(announcement -> new Match(foodRequest, announcement))
                .collect(Collectors.toList());
    }

    public List<Match> findMatchesByDonation(UUID announcementId) {
        FoodAnnouncement foodaAnnouncement = foodAnnouncementClient.getDonationById(announcementId);
        if (foodaAnnouncement == null) {
            throw new IllegalArgumentException("Food request not found with id: " + announcementId);
        }

        List<FoodRequest> foodRequest = foodRequestClient.getAllRequests();
        return foodRequest.stream()
                .filter(request -> isMatch(request, foodaAnnouncement))
                .map(request -> new Match(request, foodaAnnouncement))
                .collect(Collectors.toList());
    }

    public List<Match> findMatchesByCoordinates(double latitude, double longitude, double distance) {
        List<FoodAnnouncement> foodAnnouncements = foodAnnouncementClient.getAllDonations();
        return foodAnnouncements.stream()
                .filter(announcement -> isWithinDistance(latitude, longitude, announcement, distance))
                .map(announcement -> new Match(null, announcement)) // No request associated
                .collect(Collectors.toList());
    }

    public void markRequestAsFulfilled(UUID requestId) {
        foodRequestClient.updateStatus(requestId, "FULFILLED");
    }

    public void markRequestAsCancelled(UUID requestId) {
        foodRequestClient.updateStatus(requestId, "CANCELLED");
    }

    public void markRequestAsPending(UUID requestId) {
        foodRequestClient.updateStatus(requestId, "MATCHED");
    }

    public void markRequestAsAvailable(UUID requestId) {
        foodRequestClient.updateStatus(requestId, "AVAILABLE");
    }

    public void markAnnouncementAsFulfilled(UUID announcementId) {
        foodAnnouncementClient.updateStatus(announcementId, "FULFILLED");
    }

    public void markAnnouncementAsExpired(UUID announcementId) {
        foodAnnouncementClient.updateStatus(announcementId, "EXPIRED");
    }

    public void markAnnouncementAsAvailable(UUID announcementId) {
        foodAnnouncementClient.updateStatus(announcementId, "AVAILABLE");
    }

    public void markAnnouncementAsMatched(UUID announcementId) {
        foodAnnouncementClient.updateStatus(announcementId, "MATCHED");
    }

    public void updateRequestStatus(UUID requestId, String status) {
        foodRequestClient.updateStatus(requestId, status);
    }

    public void updateAnnouncementStatus(UUID announcementId, String status) {
        foodAnnouncementClient.updateStatus(announcementId, status);
    }

    private boolean isMatch(FoodRequest request, FoodAnnouncement announcement) {

        logger.info("Request : {} , donation {}", request.getId(), announcement.getId());
        // Check if food categories match
        if (request.getFoodCategory() != null
                && !request.getFoodCategory().equalsIgnoreCase(announcement.getFoodCategory())) {
            return false;
        }

        // Check if food types match
        if (request.getFoodType() != null && !request.getFoodType().equalsIgnoreCase(announcement.getFoodType())) {
            return false;
        }
        // TODO
        // Check if the quantity requested is less than or equal to the quantity
        // available
        // if (request.getQuantity() != null && request.getQuantity() >
        // announcement.getQuantity()) {
        // return false;
        // }

        return true;
    }

    private boolean isWithinDistance(double latitude, double longitude, FoodAnnouncement announcement,
            double distance) {
        double earthRadius = 6371; // Radius of the Earth in kilometers
        double dLat = Math.toRadians(announcement.getLatitude() - latitude);
        double dLon = Math.toRadians(announcement.getLongitude() - longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(announcement.getLatitude())) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distanceInKm = earthRadius * c;
        return distanceInKm <= distance;
    }

    public FoodAnnouncement findByAnnouncement(UUID id) {
        FoodAnnouncement foodAnnouncements = foodAnnouncementClient.getDonationById(id);

        return foodAnnouncements;

    }
}