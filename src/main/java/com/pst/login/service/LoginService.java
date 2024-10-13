package com.pst.login.service;

import com.pst.login.request.EmailRequest;
import com.pst.login.request.UserRequest;
import com.pst.login.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
public class LoginService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user_service_url}")
    private String userServiceUrl;

    @Value("${message_service_url}")
    private String messageServiceUrl;

    public String generateAndSendOtp(long aadhaarNumber) {

        UserResponse userResponse = restTemplate.getForObject(userServiceUrl + aadhaarNumber, UserResponse.class);

        if (userResponse == null) {
            return "User not found.";
        }

        int otp = 100000 + new Random().nextInt(900000);

        UserRequest userRequest = new UserRequest(userResponse);
        userRequest.setOtp(otp);


        restTemplate.put(userServiceUrl + "/update-user/" + aadhaarNumber, userRequest, UserResponse.class);

        EmailRequest emailRequest = new EmailRequest(userResponse.getEmail(), "Your OTP Code", "Your OTP is: " + otp);
        String messageServiceResponse = restTemplate.postForObject(messageServiceUrl, emailRequest, String.class);


        return (messageServiceResponse == null) ? "Failed to send OTP" : "OTP sent to: " + userResponse.getEmail();

    }

}
