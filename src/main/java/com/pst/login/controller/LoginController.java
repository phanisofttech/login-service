package com.pst.login.controller;

import com.pst.login.request.LoginRequest;
import com.pst.login.response.LoginResponse;
import com.pst.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "*")
public class LoginController {

	@Autowired
	private LoginService loginService;

	/**
	 * It generate and send the otp via email
	 * @param aadhaarNumber
	 * @return {@link LoginResponse}
	 */
	@PostMapping("/otp/{aadhaarNumber}")
	public LoginResponse generateAndSendOtp(@PathVariable long aadhaarNumber) {
		return loginService.generateAndSendOtp(aadhaarNumber);
	}

	/**
	 * It generates and send the password via email and also Stored in database
	 * @param aadhaarNumber
	 * @param otp
	 * @return {@link LoginResponse}
	 */
	@PostMapping("/password/{aadhaarNumber}/{otp}")
	public LoginResponse generateAndSendPassword(@PathVariable long aadhaarNumber, @PathVariable int otp) {
		return loginService.generateAndSendPassword(aadhaarNumber, otp);
	}

	/**
	 * It verify password and navigate to the dashboard page
	 * @param loginRequest
	 * @return {@link LoginResponse}
	 */
	@PostMapping
	public LoginResponse verifyPasswordAndLogin(@RequestBody LoginRequest loginRequest) {
		return loginService.verifyPasswordAndLogin(loginRequest);
	}

}
