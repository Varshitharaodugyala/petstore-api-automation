package com.veeva.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*
 *POJO class representing User object for PetStore User APIs
 * Used for serialization (Java → JSON) when sending request
 * and deserialization (JSON → Java) when reading response
 * ignoreUnknown = true prevents failure if API response has extra fields
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int userStatus;

    // Default constructor required for JSON → Java conversion
    public User() {}
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    // Getter and Setter for username,firstName,lastName,email,password,phone,userStatus

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public int getUserStatus() { return userStatus; }
    public void setUserStatus(int userStatus) { this.userStatus = userStatus; }


}