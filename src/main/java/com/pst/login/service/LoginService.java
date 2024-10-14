package com.pst.login.service;

import com.pst.login.request.EmailRequest;
import com.pst.login.request.UserRequest;
import com.pst.login.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Random;
import java.util.UUID;

@Service
public class LoginService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user_service_url}")
    private String userServiceUrl;

    @Value("${message_service_url}")
    private String messageServiceUrl;

    private final String updateUserUrl = "/update-user/";
    
    private String password;

    public String generateAndSendOtp(long aadhaarNumber) {

        UserResponse userResponse = restTemplate.getForObject(userServiceUrl + aadhaarNumber, UserResponse.class);

        if (userResponse == null) {
            return "User not found.";
        }

        int otp = 100000 + new Random().nextInt(900000);

        UserRequest userRequest = new UserRequest(userResponse);
        userRequest.setOtp(otp);

        HttpEntity<UserRequest> requestEntity = new HttpEntity<>(userRequest);

        ResponseEntity<UserResponse> response = restTemplate.exchange(
                userServiceUrl + updateUserUrl + aadhaarNumber,
                HttpMethod.PUT,
                requestEntity,
                UserResponse.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            return "Failed to update user.";
        }

        EmailRequest emailRequest = new EmailRequest(userResponse.getEmail(), "Your OTP Code", "Your OTP is: " + otp);
        String messageServiceResponse = restTemplate.postForObject(messageServiceUrl, emailRequest, String.class);

        return (messageServiceResponse == null) ? "Failed to send OTP" : "OTP sent to: " + userResponse.getEmail();
    }
    
	/**It generates the password and it will send to the email**/
	public String generateAndSendPassword(long aadhaarNumber, int otp) {

		String userApiUrl = userServiceUrl + aadhaarNumber;

		ResponseEntity<UserResponse> userApiResponse = restTemplate.getForEntity(userApiUrl, UserResponse.class);

		if (userApiResponse.getBody() == null) {
			return "User Not Found With The Given Aadhaar Number";
		}

		if (userApiResponse.getBody().getOtp() == otp) {
			password = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
		} else {
			return "Incorrect OTP";
		}

		UserRequest userRequest = new UserRequest(userApiResponse.getBody());
		userRequest.setPassword(password);

		HttpEntity<UserRequest> requestEntity = new HttpEntity<>(userRequest);

		ResponseEntity<UserResponse> userApiUpdateResponse = restTemplate.exchange(
				userServiceUrl + updateUserUrl + aadhaarNumber,
				HttpMethod.PUT, 
				requestEntity, 
				UserResponse.class);
		
		if (userApiUpdateResponse.getBody() == null) {
			return "Password not updated";
		}

		EmailRequest emailRequest = new EmailRequest();
		emailRequest.setToEmail(userApiResponse.getBody().getEmail());
		emailRequest.setBody("Your password is : " + password);
		emailRequest.setSubject("Login Service Password");

		ResponseEntity<String> emailApiResponse = restTemplate.postForEntity(messageServiceUrl, emailRequest,
				String.class);
		if (emailApiResponse.getBody().isEmpty()) {
			return "Email not Sent";
		} else {
			return "Password Sent to " + emailRequest.getToEmail() + " ! Check Once ";
		}

	}
}
