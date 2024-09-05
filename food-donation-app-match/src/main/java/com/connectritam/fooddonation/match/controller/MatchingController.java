package com.connectritam.fooddonation.match.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.connectritam.fooddonation.match.model.Match;
import com.connectritam.fooddonation.match.service.MatchingService;

@RestController
@RequestMapping("/v1/matches")
public class MatchingController {

    @Autowired
    private MatchingService matchingService;

    @GetMapping("/{requestId}")
    public ResponseEntity<List<Match>> getMatches(@PathVariable UUID requestId) {
        List<Match> matches = matchingService.findMatches(requestId);
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/by-distance")
    public ResponseEntity<List<Match>> getMatchesByCoordinates(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam double distance) {
        List<Match> matches = matchingService.findMatchesByCoordinates(latitude, longitude, distance);
        return ResponseEntity.ok(matches);
    }

    // Endpoints for Food Requests

    @PutMapping("/requests/{id}/fulfill")
    public ResponseEntity<Void> fulfillRequest(@PathVariable UUID id) {
        matchingService.markRequestAsFulfilled(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/requests/{id}/cancel")
    public ResponseEntity<Void> cancelRequest(@PathVariable UUID id) {
        matchingService.markRequestAsCancelled(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/requests/{id}/pending")
    public ResponseEntity<Void> markRequestAsPending(@PathVariable UUID id) {
        matchingService.markRequestAsPending(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoints for Food Announcements

    @PutMapping("/announcements/{id}/fulfill")
    public ResponseEntity<Void> fulfillAnnouncement(@PathVariable UUID id) {
        matchingService.markAnnouncementAsFulfilled(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/announcements/{id}/available")
    public ResponseEntity<Void> markAnnouncementAsAvailable(@PathVariable UUID id) {
        matchingService.markAnnouncementAsAvailable(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/requests/{id}/status")
    public ResponseEntity<Void> updateRequestStatus(@PathVariable UUID id, @RequestParam String status) {
        matchingService.updateRequestStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    // Endpoints for Food Announcements

    @PutMapping("/announcements/{id}/status")
    public ResponseEntity<Void> updateAnnouncementStatus(@PathVariable UUID id, @RequestParam String status) {
        matchingService.updateAnnouncementStatus(id, status);
        return ResponseEntity.noContent().build();
    }

}