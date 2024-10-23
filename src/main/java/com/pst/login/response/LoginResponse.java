package com.pst.login.response;

import org.springframework.http.HttpStatusCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

	@JsonProperty private String message;

	@JsonProperty private HttpStatusCode statusCode;

	@JsonProperty private int statusCodeValue;

}
