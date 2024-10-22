package com.pst.login.controller;

import com.pst.login.request.LoginRequest;
import com.pst.login.response.MessageResponse;
import com.pst.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    
    @Autowired
    private MessageResponse messageResponse;

    @PostMapping("/otp/{aadhaarNumber}")
    public ResponseEntity<MessageResponse> generateAndSendOtp(@PathVariable long aadhaarNumber) {
        String otpMessage = loginService.generateAndSendOtp(aadhaarNumber);
        messageResponse.setMessage(otpMessage);
        return  new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.OK);
    }
    

    /**
     * It generates and send the password via email and also Stored in database 
     * @param aadhaarNumber
     * @param otp
     * @return ResponseEntity<MessageResponse> 
     */
   	@PostMapping("/password/{aadhaarNumber}/{otp}")
   	public ResponseEntity<MessageResponse> generateAndSendPassword(@PathVariable long aadhaarNumber, @PathVariable int otp) {
   		String passwordMessage = loginService.generateAndSendPassword(aadhaarNumber, otp);
   		messageResponse.setMessage(passwordMessage);
   		return new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.OK);
   	}

   	/**
   	 * It verify password and navigate to the dashboard page
   	 * @param loginRequest
   	 * @return ResponseEntity<MessageResponse> 
   	 */
	@PostMapping
	public ResponseEntity<MessageResponse> verifyPasswordAndLogin(@RequestBody LoginRequest loginRequest) {
		String loginMessage = loginService.verifyPasswordAndLogin(loginRequest);
		messageResponse.setMessage(loginMessage);
		return new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.OK);
	}

}
