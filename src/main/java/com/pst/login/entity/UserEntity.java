package com.pst.login.entity;

import java.sql.Blob;
import java.sql.Date;

import com.pst.login.response.UserResponse;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
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
    private Blob photo;
    private String createdBy;
    private String updatedBy;
    private Date createdAt;
    private Date updatedAt;

    // Default Constructor
    public UserEntity() {
    }

    // Constructor to create UserEntity from UserResponse
    public UserEntity(UserResponse userResponse) {
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
        this.photo = userResponse.getPhoto();
        this.createdBy = userResponse.getCreatedBy();
        this.updatedBy = userResponse.getUpdatedBy();
        this.createdAt = userResponse.getCreatedAt();
        this.updatedAt = userResponse.getUpdatedAt();
    }

    // Getters and Setters
    public long getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(long aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getMandal() {
        return mandal;
    }

    public void setMandal(String mandal) {
        this.mandal = mandal;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
