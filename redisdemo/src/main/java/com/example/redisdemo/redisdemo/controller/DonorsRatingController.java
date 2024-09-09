package com.example.redisdemo.redisdemo.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.redisdemo.redisdemo.model.DonorsRating;
import com.example.redisdemo.redisdemo.service.DonorsRatingService;

@RestController
@RequestMapping("/api/v1/donors-rating")
public class DonorsRatingController {

    private static final Logger logger = LoggerFactory.getLogger(DonorsRatingController.class);

    @Autowired
    DonorsRatingService donorsRatingService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/{id}")
    public ResponseEntity<DonorsRating> get(@PathVariable UUID id) {

        return ResponseEntity.ok(donorsRatingService.getDonorRatingById(id));

    }

    @PostMapping
    public ResponseEntity<DonorsRating> postMethodName(@RequestBody DonorsRating entity) {
        donorsRatingService.saveDonorRating(entity);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        donorsRatingService.deleteDonorRating(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping
    public ResponseEntity<List<DonorsRating>> findAll() {
        donorsRatingService.findAll();
        return ResponseEntity.ok(donorsRatingService.findAll());
    }

    @DeleteMapping("/flush")
    public String flushAllKeys() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
        logger.info("Redis cache cleared on application startup.");

        return "All cache keys have been flushed!";
    }

}
