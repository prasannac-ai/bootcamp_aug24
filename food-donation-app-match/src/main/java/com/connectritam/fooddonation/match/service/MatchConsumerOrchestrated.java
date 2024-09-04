package com.connectritam.fooddonation.match.service;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.connectritam.fooddonation.match.model.FoodAnnouncement;
import com.connectritam.fooddonation.match.model.Notifications;
import com.connectritam.fooddonation.match.model.Users;
import com.connectritam.fooddonation.match.security.OauthService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MatchConsumerOrchestrated {

        Logger logger = LoggerFactory.getLogger(MatchConsumerOrchestrated.class);
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Autowired
        OauthService oauthService;

        @Autowired
        private RestTemplate restTemplate;

        @KafkaListener(topics = "donation", groupId = "group_id")
        public void handleDonationNotification(String message) {

                try {
                        // FIXIT : Add the logic to send notification to the user

                } catch (Exception e) {

                        e.printStackTrace();
                }

        }

}
