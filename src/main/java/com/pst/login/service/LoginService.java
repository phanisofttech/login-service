package com.pst.login.service;

import com.pst.login.entity.UserEntity;
import com.pst.login.repository.LoginRepository;
import com.pst.login.request.EmailRequest;
import com.pst.login.response.UserResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
public class LoginService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoginRepository loginRepository;


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

        UserEntity userEntity = new UserEntity(userResponse);
        userEntity.setOtp(otp);
        loginRepository.save(userEntity);


        EmailRequest emailRequest = new EmailRequest(userResponse.getEmail(), "Your OTP Code", "Your OTP is: " + otp);
        String messageServiceResponse = restTemplate.postForObject(messageServiceUrl, emailRequest, String.class);


        return (messageServiceResponse == null) ? "Failed to send OTP" : "OTP sent to: " + userResponse.getEmail();
    }

}
