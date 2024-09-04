package com.connectritam.fooddonation.match.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OauthService {

    @Value("${access.token}")
    String accessToken;

    public String getAccessToken() {
        // FIXIT - Change this to talk to oauth server and get the token. this is only
        // for demonstration
        return accessToken;
    }

}
