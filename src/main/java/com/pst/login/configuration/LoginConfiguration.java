package com.pst.login.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.pst.login.response.MessageResponse;

@Configuration
public class LoginConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public MessageResponse messageResponse() {
    	return new MessageResponse();
    }
}
