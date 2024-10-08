package com.connectritam.fooddonation.foodannouncement.service;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DonationProducer {

    Logger logger = LoggerFactory.getLogger(DonationProducer.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "donation";

    public void sendMessage(String donation) {

        try {
            logger.info("Trying to send message to kafka topic" + donation);

            // Donation is created successfully, it is not a concern, if the notification
            // fails.
            Map<String, Object> message = new HashMap<>();
            message.put("donationId", donation);
            kafkaTemplate.send(TOPIC, message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
