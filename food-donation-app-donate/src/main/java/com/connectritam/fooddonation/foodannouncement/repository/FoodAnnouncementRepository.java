package com.connectritam.fooddonation.foodannouncement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.connectritam.fooddonation.foodannouncement.model.FoodAnnouncement;

@Repository
public interface FoodAnnouncementRepository extends JpaRepository<FoodAnnouncement, UUID> {
}