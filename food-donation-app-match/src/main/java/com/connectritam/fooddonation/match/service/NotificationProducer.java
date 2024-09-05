package com.connectritam.fooddonation.match.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.connectritam.fooddonation.match.model.Notifications;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotificationProducer {

    private static final Logger logger = LoggerFactory.getLogger(NotificationProducer.class);
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    private static final String TOPIC = "notifications";

    public void sendMessage(Notifications notification) {

        try {

            String notificationJson = objectMapper.writeValueAsString(notification);

            kafkaTemplate.send(TOPIC, notificationJson);

            logger.info("Notification produce "+notificationJson);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
