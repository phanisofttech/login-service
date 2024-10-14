package com.pst.login.request;

import com.pst.login.response.UserResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Blob;
import java.sql.Date;

@NoArgsConstructor
@Data
public class UserRequest {
    private long aadhaarNumber;
    private String userFirstName;
    private String userLastName;
    private String gender;
    private Date dateOfBirth;
    private long mobileNumber;
    private String email;
    private String addressLine1;
    private String village;
    private String mandal;
    private String district;
    private int pin;
    private String state;
    private String country;
    private int otp;
    private String password;
    private Blob photo;
    private String createdBy;
    private String updatedBy;
    private Date createdAt;
    private Date updatedAt;

    // Constructor to create UserRequest from UserResponse
    public UserRequest(UserResponse userResponse) {
        this.aadhaarNumber = userResponse.getAadhaarNumber();
        this.userFirstName = userResponse.getUserFirstName();
        this.userLastName = userResponse.getUserLastName();
        this.gender = userResponse.getGender();
        this.dateOfBirth = userResponse.getDateOfBirth();
        this.mobileNumber = userResponse.getMobileNumber();
        this.email = userResponse.getEmail();
        this.addressLine1 = userResponse.getAddressLine1();
        this.village = userResponse.getVillage();
        this.mandal = userResponse.getMandal();
        this.district = userResponse.getDistrict();
        this.pin = userResponse.getPin();
        this.state = userResponse.getState();
        this.country = userResponse.getCountry();
        this.password = userResponse.getPassword();
        this.photo = userResponse.getPhoto();
        this.createdBy = userResponse.getCreatedBy();
        this.updatedBy = userResponse.getUpdatedBy();
        this.createdAt = userResponse.getCreatedAt();
        this.updatedAt = userResponse.getUpdatedAt();
    }
}
