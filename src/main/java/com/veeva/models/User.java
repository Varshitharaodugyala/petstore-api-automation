package com.veeva.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// POJO class representing User object for Petstore User APIs
// Used for serialization (Java → JSON) when sending request
// and deserialization (JSON → Java) when reading response
// ignoreUnknown = true prevents failure if API response has extra fields
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    // Unique user id
    private long id;

    // Username used for login
    private String username;

    // User first name
    private String firstName;

    // User last name
    private String lastName;

    // User email address
    private String email;

    // User password
    private String password;

    // User phone number
    private String phone;

    // User status (like active/inactive)
    private int userStatus;

    // Default constructor required for JSON → Java conversion
    public User() {}

    // Getter and Setter for id
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    // Getter and Setter for username
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    // Getter and Setter for firstName
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    // Getter and Setter for lastName
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    // Getter and Setter for email
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Getter and Setter for password
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Getter and Setter for phone
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    // Getter and Setter for userStatus
    public int getUserStatus() { return userStatus; }
    public void setUserStatus(int userStatus) { this.userStatus = userStatus; }
}