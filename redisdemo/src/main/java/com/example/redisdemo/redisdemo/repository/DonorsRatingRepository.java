package com.example.redisdemo.redisdemo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.redisdemo.redisdemo.model.DonorsRating;

public interface DonorsRatingRepository extends JpaRepository<DonorsRating, UUID> {

}
