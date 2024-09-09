package com.example.redisdemo.redisdemo.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.redisdemo.redisdemo.model.DonorsRating;
import com.example.redisdemo.redisdemo.repository.DonorsRatingRepository;

import brave.Span;
import brave.Tracer;

@Service
public class DonorsRatingService {

    @Autowired
    DonorsRatingRepository donorsRepository;

    @Autowired
    private Tracer tracer;

    Logger logger = LoggerFactory.getLogger(DonorsRatingService.class);

    @CachePut(value = "donorrating", key = "#donorsRating.id")
    public DonorsRating saveDonorRating(DonorsRating donorsRating) {
        DonorsRating savedRating = null;

        Span newSpan = tracer.nextSpan().name("saveDonorRating").start();
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(newSpan)) {

            logger.debug("Entering saveDonorRating method");
            logger.error("add to db");
            logger.debug("After adding to db");
            donorsRating.setId(UUID.randomUUID());
            savedRating = donorsRepository.save(donorsRating);
        } finally {
            newSpan.finish();
        }
        return savedRating;
    }

    @Cacheable(value = "donorrating", key = "#id")
    public DonorsRating getDonorRatingById(UUID id) {

        logger.error("from db");
        return donorsRepository.findById(id).get();
    }

    @CacheEvict(value = "donorrating", key = "#id")
    public void deleteDonorRating(UUID id) {
        donorsRepository.deleteById(id);
    }

    public List<DonorsRating> findAll() {
        return donorsRepository.findAll();
    }
}
