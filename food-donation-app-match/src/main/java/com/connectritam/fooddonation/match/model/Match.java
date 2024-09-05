package com.connectritam.fooddonation.match.model;

import java.util.UUID;

public class Match {

    private UUID requestId;
    private UUID announcementId;

    private double distance;
    private FoodRequest request;

    public FoodRequest getRequest() {
        return request;
    }

    public void setRequest(FoodRequest request) {
        this.request = request;
    }

    private FoodAnnouncement announcement;
    // Getters and setters

    public FoodAnnouncement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(FoodAnnouncement announcement) {
        this.announcement = announcement;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Match(UUID reqId, UUID donationId, double distance) {
        this.announcementId = reqId;
        this.requestId = donationId;
        this.distance = distance;
    }

    public Match(FoodRequest request, FoodAnnouncement announcement) {
        this.request = request;
        this.announcement = announcement;

    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public UUID getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(UUID announcementId) {
        this.announcementId = announcementId;
    }

}