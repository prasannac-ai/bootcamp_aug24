package com.connectritam.fooddonation.match.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.connectritam.fooddonation.match.model.FoodAnnouncement;
import com.connectritam.fooddonation.match.model.FoodRequest;
import com.connectritam.fooddonation.match.model.Match;
import com.connectritam.fooddonation.match.model.Notifications;
import com.connectritam.fooddonation.match.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MatchConsumer {

        private static final Logger logger = LoggerFactory.getLogger(MatchConsumer.class);

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Autowired
        MatchingService matchingService;

        @Autowired
        UserService userService;

        @Autowired
        private NotificationProducer notificationProducer;

        @KafkaListener(topics = "donation", groupId = "group_id")
        public void handleDonationNotification(String message) {

                Notifications notification = new Notifications();
                try {
                        Map<String, Object> donationMessage = objectMapper.readValue(message, Map.class);
                        FoodAnnouncement announcement = matchingService
                                        .findByAnnouncement(
                                                        UUID.fromString(donationMessage.get("donationId").toString()));

                        logger.info("Donation Received {} ",
                                        UUID.fromString(donationMessage.get("donationId").toString()).toString());

                        logger.info("Donation details {} ",
                                        announcement.toString());

                        List<Match> matchList = matchingService
                                        .findMatchesByDonation(
                                                        UUID.fromString(donationMessage.get("donationId").toString()));
                        logger.info("Match details {} ",
                                        matchList.toString());

                        for (Match match : matchList) {
                                FoodRequest request = match.getRequest();

                                logger.info("Match details {} ", request.getCollectorId());

                                Users user = userService.getUserById(request.getCollectorId().toString());

                                logger.info(
                                                " Dear {}, with ID {}  mail {} mobile {}, you have food request match as per your need, here is the details of the available from  {}, Food {} at {}  ",
                                                user.getName(), user.getId(),
                                                user.getEmail(), user.getMobile(), announcement.getDonorId(),
                                                announcement.getFoodCategory(),
                                                announcement.getLocation());
                                notification.setUserId(user.getId());
                                notification.setChannel("WhatsApp");
                                notification.setStatus("PENDING");
                                notification.setMessage("Food request match, stay healthy");
                                notification.setMobile(user.getMobile());
                                notification.setUserEmail(user.getEmail());

                                notificationProducer.sendMessage(notification);

                        }

                } catch (Exception e) {

                        e.printStackTrace();
                }

        }

}
