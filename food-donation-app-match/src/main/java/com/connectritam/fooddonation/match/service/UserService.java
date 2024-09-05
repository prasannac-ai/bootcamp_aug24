package com.connectritam.fooddonation.match.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.connectritam.fooddonation.match.model.Users;
import com.connectritam.fooddonation.match.security.OauthService;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    OauthService aOauthService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${shared.secret}")
    private String sharedSecret;

    public Users getUserById(String userId) {

        String url = "http://user-service:8080/api/v1/users/" + userId;
        logger.info(url);

        // Create headers if needed
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("X-Shared-Secret", sharedSecret);
        // Create the entity with headers
        headers.set("Authorization", "Bearer " + aOauthService.getAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);
        // Make the request
        ResponseEntity<Users> response = restTemplate.exchange(url, HttpMethod.GET, entity, Users.class);

        // Return the response body
        return response.getBody();
    }


    
}