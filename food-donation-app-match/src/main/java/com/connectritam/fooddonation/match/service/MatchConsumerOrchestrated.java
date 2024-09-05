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

        @KafkaListener(topics = "donation", groupId = "group_id1")
        public void handleDonationNotification(String message) {

                try {
                        /* consume donation from kafka topic */
                        Map<String, Object> donationMessage = objectMapper.readValue(message, Map.class);
                        logger.info("Donation Received {} ",
                                        UUID.fromString(donationMessage.get("donationId").toString()).toString());

                        /* get the donation details from the food-announcement-service based on id */
                        String url = "http://food-announcement-service:8080/v1/food-donations/"
                                        + UUID.fromString(donationMessage.get("donationId").toString()).toString();
                        logger.info(url);

                        /* Set required headers and call the food donation API */
                        HttpHeaders headers = new HttpHeaders();
                        headers.set("Accept", "application/json");
                        headers.set("Authorization", "Bearer " + oauthService.getAccessToken());
                        HttpEntity<String> entity = new HttpEntity<>(headers);
                        /* call API to get the donation details */
                        ResponseEntity<FoodAnnouncement> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                                        FoodAnnouncement.class);
                        response.getBody();
                        logger.info(response.getBody().toString());

                        String annoucementMessage = "Food is available and here is the details:  Food type "
                                        + response.getBody().getFoodCategory() + ",  Quanity "
                                        + response.getBody().getQuantity() + ", at  "
                                        + response.getBody().getLocation() + ", Stay blessed";
                        logger.info(annoucementMessage);

                        /* Collect user details for sending notification */

                        String userServiceURL = "http://user-service:8080/api/v1/users/"
                                        + response.getBody().getDonorId();
                        logger.info(url);
                        HttpHeaders userDetailsHeader = new HttpHeaders();
                        userDetailsHeader.set("Accept", "application/json");
                        userDetailsHeader.set("Authorization", "Bearer " + oauthService.getAccessToken());
                        HttpEntity<String> entityUser = new HttpEntity<>(userDetailsHeader);
                        ResponseEntity<Users> usersResponse = restTemplate.exchange(userServiceURL, HttpMethod.GET,
                                        entityUser,
                                        Users.class);
                        annoucementMessage = "Food is available from " + usersResponse.getBody().getName()
                                        + " and here is the details:  Food type "
                                        + response.getBody().getFoodCategory() + ",  Quantity "
                                        + response.getBody().getQuantity()
                                        + ", at  "
                                        + response.getBody().getLocation() + " . Happy Meal";
                        logger.info(annoucementMessage);

                        // String notificationServiceUrl =
                        // "http://notification-service:8080/api/v2/notification";
                        // logger.info(notificationServiceUrl);
                        // // Create HttpHeaders (if needed)
                        // HttpHeaders headersNotification = new HttpHeaders();
                        // headers.setContentType(MediaType.APPLICATION_JSON);

                        // // Create the HttpEntity containing headers and the payload
                        // Notifications notificationPayload = new Notifications();
                        // HttpEntity<Notifications> entityNotification = new
                        // HttpEntity<>(notificationPayload,
                        // headersNotification);

                        // // Send the POST request
                        // ResponseEntity<Notifications> notificationResponse = restTemplate.exchange(
                        // notificationServiceUrl,
                        // HttpMethod.POST,
                        // entityNotification,
                        // Notifications.class);

                        // // Get the response body or status code if needed

                        // HttpStatusCode responseStatus = notificationResponse.getStatusCode();

                        // if (responseStatus.is2xxSuccessful()) {
                        // logger.info("Hurray notification sent successfully");
                        // } else {
                        // logger.info(" notification send failed ");
                        // }

                } catch (Exception e) {

                        e.printStackTrace();
                }

        }

}