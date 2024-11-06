package com.pst.login.service;

import com.pst.login.request.EmailRequest;
import com.pst.login.request.LoginRequest;
import com.pst.login.request.UserRequest;
import com.pst.login.response.LoginResponse;
import com.pst.login.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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

	private LoginResponse loginResponse;

	/**
	 * It generate and send the otp via email
	 * @param aadhaarNumber
	 * @return {@link LoginResponse}
	 */
	public LoginResponse generateAndSendOtp(long aadhaarNumber) {

		UserResponse userResponse = restTemplate.getForObject(userServiceUrl + aadhaarNumber, UserResponse.class);

		if (userResponse == null) {
			return loginResponse = new LoginResponse("User not found with the Aadhaar Number", HttpStatus.NOT_FOUND,
					HttpStatus.NOT_FOUND.value());
		}

		int otp = 100000 + new Random().nextInt(900000);

		UserRequest userRequest = new UserRequest(userResponse);
		userRequest.setOtp(otp);

		HttpEntity<UserRequest> requestEntity = new HttpEntity<>(userRequest);

		ResponseEntity<UserResponse> response = restTemplate.exchange(userServiceUrl + updateUserUrl + aadhaarNumber,
				HttpMethod.PUT, requestEntity, UserResponse.class);

		if (!response.getStatusCode().is2xxSuccessful()) {
			return loginResponse = new LoginResponse("Failed to save OTP", HttpStatus.INTERNAL_SERVER_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		EmailRequest emailRequest = new EmailRequest(userResponse.getEmail(), "Your OTP Code", "Your OTP is: " + otp);
		String messageServiceResponse = restTemplate.postForObject(messageServiceUrl, emailRequest, String.class);

		if (messageServiceResponse == null) {
			return loginResponse = new LoginResponse("Failed to send OTP", HttpStatus.INTERNAL_SERVER_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value());
		} else {
			return loginResponse = new LoginResponse("OTP Sent to " + userResponse.getEmail() + " ! Check Once ",
					HttpStatus.OK, HttpStatus.OK.value());
		}
	}

	/**
	 * It generates the password and it will send to the email
	 * @param aadhaarNumber
	 * @param otp
	 * @return{@link LoginResponse}
	 */
	public LoginResponse generateAndSendPassword(long aadhaarNumber, int otp) {

		String userApiUrl = userServiceUrl + aadhaarNumber;

		ResponseEntity<UserResponse> userApiResponse = restTemplate.getForEntity(userApiUrl, UserResponse.class);

		if (userApiResponse.getBody() == null) {
			return loginResponse = new LoginResponse("User not found with the Aadhaar Number", HttpStatus.NOT_FOUND,
					HttpStatus.NOT_FOUND.value());
		}

		if (userApiResponse.getBody().getOtp() == otp) {
			password = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
		} else {
			return loginResponse = new LoginResponse("Incorrect OTP", HttpStatus.UNAUTHORIZED,
					HttpStatus.UNAUTHORIZED.value());
		}

		UserRequest userRequest = new UserRequest(userApiResponse.getBody());
		userRequest.setPassword(password);

		HttpEntity<UserRequest> requestEntity = new HttpEntity<>(userRequest);

		ResponseEntity<UserResponse> userApiUpdateResponse = restTemplate.exchange(
				userServiceUrl + updateUserUrl + aadhaarNumber, HttpMethod.PUT, requestEntity, UserResponse.class);

		if (userApiUpdateResponse.getBody() == null) {
			return loginResponse = new LoginResponse("Failed to send password", HttpStatus.INTERNAL_SERVER_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		EmailRequest emailRequest = new EmailRequest();
		emailRequest.setToEmail(userApiResponse.getBody().getEmail());
		emailRequest.setBody("Your password is : " + password);
		emailRequest.setSubject("Your Password For RDCRMS");

		ResponseEntity<String> emailApiResponse = restTemplate.postForEntity(messageServiceUrl, emailRequest,
				String.class);
		if (emailApiResponse.getBody().isEmpty()) {
			return loginResponse = new LoginResponse("Failed to send E-mail", HttpStatus.INTERNAL_SERVER_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value());
		} else {
			return loginResponse = new LoginResponse("Password Sent to " + emailRequest.getToEmail() + " ! Check Once ",
					HttpStatus.OK, HttpStatus.OK.value());
		}
	}

	/**
	 * It check password and navigate to the dashboard
	 * @param loginRequest
	 * @return {@link LoginResponse}
	 */
	public LoginResponse verifyPasswordAndLogin(LoginRequest loginRequest) {

		long aadhaarNumber = loginRequest.getAadhaarNumber();
		String password = loginRequest.getPassword();

		ResponseEntity<UserResponse> userResponse = restTemplate.getForEntity(userServiceUrl + aadhaarNumber,
				UserResponse.class);
		if (userResponse.getBody() == null) {
			return loginResponse = new LoginResponse("User not found with the Aadhaar Number", HttpStatus.NOT_FOUND,
					HttpStatus.NOT_FOUND.value());
		}
		
		if (!userResponse.getBody().getPassword().equals(password)) {
			return loginResponse = new LoginResponse("Login failed", HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
		}else {
			return loginResponse = new LoginResponse("Login successfull", HttpStatus.OK, HttpStatus.OK.value());
		}
	}

}
