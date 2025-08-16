package com.lms.model;

import java.io.InputStream;
import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String email;
    private String password;
    private String name;
    private String userType;
    private String rollNumber;
    private String department;
    private String phone;
    private String address;
    private InputStream profilePic;

    // Getters and Setters for all fields
    public InputStream getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(InputStream profilePic) {
        this.profilePic = profilePic;
    }
    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public String getRollNumber() {
        return rollNumber;
    }
    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}