package com.connectritam.fooddonation.match.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.connectritam.fooddonation.match.security.OauthService;

@Configuration
public class FeignClientConfig {

    @Value("${shared.secret}")
    private String sharedSecret;

    @Autowired
    OauthService oauthService;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes();
                if (requestAttributes != null) {
                    HttpServletRequest request = requestAttributes.getRequest();
                    // Get the Authorization header from the original request
                    String authorizationHeader = request.getHeader("Authorization");
                    if (sharedSecret != null) {
                        template.header("X-Shared-Secret", sharedSecret);
                    }
                    if (authorizationHeader != null) {
                        // Add the Authorization header to the Feign request
                        template.header("Authorization", authorizationHeader);
                    }
                } else {
                    template.header("X-Shared-Secret", sharedSecret);
                    template.header("Authorization", "Bearer " + oauthService.getAccessToken());
                }

            }
        };
    }
}