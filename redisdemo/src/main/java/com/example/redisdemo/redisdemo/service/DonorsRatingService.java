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

            logger.info("Entering saveDonorRating method");
            logger.info("add to db");
            logger.info("After adding to db");
            donorsRating.setId(UUID.randomUUID());
            savedRating = donorsRepository.save(donorsRating);
        } finally {
            newSpan.finish();
        }
        return savedRating;
    }

    @Cacheable(value = "donorrating", key = "#id")
    public DonorsRating getDonorRatingById(UUID id) {

        try {
            // Induce delay and test the cache
            Thread.sleep(5000);
        } catch (InterruptedException e) {

            logger.error("Thread interrupted", e);
        }

        logger.info("from db");
        return donorsRepository.findById(id).get();
    }

    @CacheEvict(value = "donorrating", key = "#id")
    public void deleteDonorRating(UUID id) {
        donorsRepository.deleteById(id);
    }

    @Cacheable(value = "donorrating", key = "'findAll'")
    public List<DonorsRating> findAll() {
        logger.info("Fetching all donors ratings from db");
        return donorsRepository.findAll();

   
    }


}
